package khan.pet.Service;

import khan.pet.entity.Blank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Sawmill {

    private static final Map<Integer, Integer> PRODUCT_PER_METER = new HashMap<>();

    static {
        PRODUCT_PER_METER.put(200, 3);
        PRODUCT_PER_METER.put(500, 7);
        PRODUCT_PER_METER.put(700, 12);
    }

    public static Map<String, Integer> calculatePlanks(List<Blank> blanks) {
        return blanks.stream()
                .collect(Collectors.groupingBy(Blank::getType, Collectors.summingInt(Sawmill::extracted)));
    }

    private static int extracted(Blank blank) {
        int blPerMeter = PRODUCT_PER_METER.getOrDefault(blank.getDiameter(), 0);
        int useLength = blank.getLength() / 2;
        return blPerMeter * useLength;
    }

}
