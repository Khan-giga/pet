package khan.pet.Service;

import khan.pet.entity.Blank;
import khan.pet.exception.UnknownWoodException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class GroupProcessorService {

    private final List<List<Blank>> blankGroup;
    private String outputCsv = "src/main/resources/";

    public GroupProcessorService(List<List<Blank>> blankGroup) {
        this.blankGroup = blankGroup;
    }

    public GroupProcessorService(List<List<Blank>> blankGroup, String outputCsv) {
        this.blankGroup = blankGroup;
        this.outputCsv = outputCsv;
    }

    public void processAllGroup() {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        for (List<Blank> blanks : blankGroup) {
            String type = blanks.stream()
                    .findFirst()
                    .orElseGet(() -> new Blank("", 0, 0))
                    .getType();
            if (type == null) {
                throw new UnknownWoodException("Неизвестный тип заготовки");
            }
            Path path = Paths.get(outputCsv + type + ".csv");
            pool.submit(() -> {
                try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                    for (Map.Entry<String, Integer> entry : Sawmill.calculatePlanks(blanks).entrySet()) {
                        writer.write(String.format("%s,%d", entry.getKey(), entry.getValue()));
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        pool.shutdown();
        try {
            if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
                pool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pool.shutdownNow();
            Thread.currentThread().interrupt();
        }

    }

}
