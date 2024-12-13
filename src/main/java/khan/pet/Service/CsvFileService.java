package khan.pet.Service;

import khan.pet.dto.request.Blank;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CsvFileService {
    private final String inputCsv;
    private final String outputCsv;

    public CsvFileService() {
        inputCsv = "src/main/resources/input.csv";
        outputCsv = "src/main/resources/output.csv";
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

}
