package khan.pet.Service;

import khan.pet.entity.Blank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SawmillTest {

    private List<Blank> blanks;

    @BeforeEach
    void setUp() {
        blanks = new ArrayList<>(Arrays.asList(
                new Blank("Дуб", 500, 14),
                new Blank("Дуб", 700, 5),
                new Blank("Дуб", 200, 3),
                new Blank("Ель", 500, 7),
                new Blank("Ель", 200, 9),
                new Blank("Сосна", 500, 10)));
    }

    @Test
    void getTypeBlankAndCountPlanksFromListBlanks() {

        Map<String, Integer> plankMap = Sawmill.calculatePlanks(blanks);

        assertEquals(3, plankMap.size());
        assertEquals(76, plankMap.get("Дуб"));
        assertEquals(33, plankMap.get("Ель"));
        assertEquals(35, plankMap.get("Сосна"));

    }

    @Test
    void negativeGetTypeBlankAndCountPlanksFromListBlanks() {

        Map<String, Integer> plankMap = Sawmill.calculatePlanks(blanks);

        assertNotEquals(5, plankMap.size());
        assertNotEquals(73, plankMap.get("Дуб"));
        assertNotEquals(55, plankMap.get("Ель"));
        assertNotEquals(44, plankMap.get("Сосна"));

    }

    @Test
    void getEmptyResultWhereListIsEmpty() {
        List<Blank> blanks = new ArrayList<>();
        Map<String, Integer> plankMap = Sawmill.calculatePlanks(blanks);

        assertTrue(plankMap.isEmpty());

    }

    @Test
    void calculatePlanksWithNullBlanks(){

        assertThrows(NullPointerException.class, () -> Sawmill.calculatePlanks(null));

    }

}