package khan.pet.Service;

import khan.pet.dto.Blank;
import khan.pet.dto.request.RequestPartDto;
import khan.pet.entity.FileStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class CsvFileService {
    private String inputCsv;
    private String outputCsv;
    private final ExecutorService executorService = Executors.newFixedThreadPool(2);

    private PartService partService;
    private FileStatusService fileStatusService;

    public CsvFileService() {
        inputCsv = "src/main/resources/input.csv";
        outputCsv = "src/main/resources/output.csv";
    }

    @Autowired
    public CsvFileService(PartService partService, FileStatusService fileStatusService) {
        this.partService = partService;
        this.fileStatusService = fileStatusService;
    }

    public CsvFileService(String inputCsv, String outputCsv) {
        this.inputCsv = inputCsv;
        this.outputCsv = outputCsv;
    }

    private Map<String, Integer> csvReader() {
        Path path = Paths.get(inputCsv);
        List<Blank> blanks = null;

        try {
            blanks = Files.readAllLines(path).stream()
                    .map(str -> {
                        String[] fields = str.split(",");
                        return new Blank(fields[0], Integer.parseInt(fields[1]), Long.parseLong(fields[2]));
                    })
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Sawmill.calculatePlanks(blanks);
    }

    public void csvSaver() {
        Path path = Paths.get(outputCsv);

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (Map.Entry<String, Integer> entry : csvReader().entrySet()) {
                writer.write(String.format("%s,%d", entry.getKey(), entry.getValue()));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String startAsyncProcessing(MultipartFile file) {

        FileStatus fileStatus = new FileStatus();
        fileStatus.setName(file.getOriginalFilename());
        fileStatus.setStatus("Принят в обработку");

        FileStatus persFileStatus = fileStatusService.saveFileStatus(fileStatus);
        CompletableFuture.runAsync(() -> {
            try {
                Map<Long, List<Blank>> map = processCsv(file);
                List<RequestPartDto> partDtos = new ArrayList<>();
                map.forEach((k, v) -> partDtos.add(RequestPartDto.builder()
                        .partyNumber(k)
                        .blanks(v)
                        .build()));
                partDtos.forEach(partService::savePart);

                persFileStatus.setName("Обработан");
                fileStatusService.saveFileStatus(persFileStatus);
            } catch (Exception e) {
                persFileStatus.setStatus("Обработан частично");
                persFileStatus.setComment(e.getMessage());
                fileStatusService.saveFileStatus(persFileStatus);
            }

        });

        return "Файл: " + file.getOriginalFilename() + " принят в обработку!";

    }

    private Map<Long, List<Blank>> processCsv(MultipartFile file) {
        Map<Long, List<Blank>> map = new ConcurrentHashMap<>();
        List<Future<?>> futureList = new ArrayList<>();
        AtomicInteger filedLines = new AtomicInteger();
        Set<Long> errorParties = new HashSet<>();
        long fileSize;

        try (BufferedReader reader = Files.newBufferedReader(saveToTemplateFile(file), StandardCharsets.UTF_8)) {
            String header = reader.readLine();
            if (header == null || !header.equals("part_number, type, diameter, length")) {
                throw new IllegalArgumentException("Некорректный формат");
            }
            fileSize = reader.lines().count() - 1;

            String line;
            while ((line = reader.readLine()) != null) {
                final String finalLine = line;
                Future<?> future = executorService.submit(() -> {
                    lineProcess(finalLine, map, filedLines, errorParties);
                });

                futureList.add(future);

            }

            future(futureList);

        } catch (IOException | ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (!errorParties.isEmpty()) {
            String message = "Обработано %d партий из %d. Ошибки возникли при обработке партий: %s".formatted(
                    filedLines.get(), fileSize, errorParties.stream()
                            .map(String::valueOf)
                            .collect(Collectors.joining(", "))
            );

            throw new RuntimeException(message);

        }

        return map;

    }

    private void lineProcess(String finalLine, Map<Long, List<Blank>> map, AtomicInteger filedLines, Set<Long> errorParties) {
        try {
            String[] fields = finalLine.split(",");
            if (fields.length != 4) {
                throw new IllegalArgumentException("Некорректный формат");
            }

            Long partNumber = Long.parseLong(fields[0]);
            String type = fields[1];
            Integer diameter = Integer.parseInt(fields[2]);
            Long length = Long.parseLong(fields[3]);

            Blank blank = Blank.builder()
                    .type(type)
                    .diameter(diameter)
                    .length(length)
                    .build();

            map.computeIfAbsent(partNumber, k -> new ArrayList<>()).add(blank);
            filedLines.incrementAndGet();
        } catch (IllegalArgumentException e) {
            errorParties.add(Long.parseLong(finalLine.split(",")[0]));
            throw new RuntimeException("Ошибка при обработке строки " + e.getMessage());
        }
    }

    private void future(List<Future<?>> futureList) throws InterruptedException, ExecutionException {
        for (Future<?> future : futureList) {
            future.get();
        }
    }

    private Path saveToTemplateFile(MultipartFile file) throws IOException {
        Path path = Files.createTempFile("upload", ".csv");
        Files.write(path, file.getBytes());
        return path;
    }
}
