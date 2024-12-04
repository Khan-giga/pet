package khan.pet.Service;

import khan.pet.entity.Blank;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupProcessorServiceTest {

    private static GroupProcessorService processorService;


    @BeforeAll
    static void setUp() {
        String path = "src/test/resources/group/";
        List<List<Blank>> blankGroup = new ArrayList<>();
        blankGroup.add(Arrays.asList(
                new Blank("Дуб", 500, 14),
                new Blank("Дуб", 700, 5),
                new Blank("Дуб", 200, 3)
        ));
        blankGroup.add(Arrays.asList(
                new Blank("Ель", 500, 7),
                new Blank("Ель", 200, 9)
        ));
        blankGroup.add(Arrays.asList(
                new Blank("Сосна", 500, 10)
        ));

        processorService = new GroupProcessorService(blankGroup, path);
    }

    @BeforeEach
    void clearDirectory() {
        Path path = Paths.get("src/test/resources/group");
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
            for (Path filePath : stream) {
                Files.delete(filePath);
                System.out.println("Удалён файл: " + filePath.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    void savedDataFromListToCsvFile() throws IOException {
        processorService.processAllGroup();
        Path path = Paths.get("src/test/resources/group/Дуб.csv");
        assertTrue(Files.size(path) > 0);
    }

}