package khan.pet.Service;

import khan.pet.entity.Blank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvFileServiceTest {

    private CsvFileService csvFileService;

    @BeforeEach
    void setUp() throws IOException {
        csvFileService = new CsvFileService("src/test/resources/input.csv",
                "src/test/resources/output.csv");

        Path path = Paths.get("src/test/resources/output.csv");
        Files.deleteIfExists(path);
    }

    @Test
    void savedDataToCsvFile() throws IOException {
        csvFileService.csvSaver();
        Path path = Paths.get("src/test/resources/output.csv");

        assertTrue(Files.size(path) > 0);

        List<String> lines = Files.readAllLines(path);
        Map<String, Integer> plankMap = lines.stream()
                .map(str -> Arrays.stream(str.split(",")).collect(Collectors.toList()))
                .collect(Collectors.toMap(l1 -> l1.get(0), l1 -> Integer.parseInt(l1.get(1)), (a, b) -> a));


        assertEquals(3, plankMap.size());
        assertEquals(76, plankMap.get("Дуб"));
        assertEquals(33, plankMap.get("Ель"));
        assertEquals(35, plankMap.get("Сосна"));

    }

}