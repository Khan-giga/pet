package khan.pet.Service;

import khan.pet.entity.Blank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sawmill {

    private static final Map<Integer, Integer> PRODUCT_PER_METER = new HashMap<>();

    static {
        PRODUCT_PER_METER.put(200, 3);
        PRODUCT_PER_METER.put(500, 7);
        PRODUCT_PER_METER.put(700, 12);
    }

    public static Map<String, Integer> calculatePlanks(List<Blank> blanks) {

        Map<String, Integer> result = new HashMap<>();

        for (Blank blank : blanks) {
            int blPerMeter = PRODUCT_PER_METER.getOrDefault(blank.getDiameter(), 0);
            int useLength = blank.getLength() / 2;
            int planks = blPerMeter * useLength;
            result.put(blank.getType(), result.getOrDefault(blank.getType(), 0) + planks);
        }

        return result;

    }

    public void test() {

    }

}
