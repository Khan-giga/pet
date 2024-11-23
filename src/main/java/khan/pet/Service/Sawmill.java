package khan.pet.Service;

import khan.pet.entity.Blank;
import khan.pet.exception.UnknownWoodException;

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
        int count = 0;

        for (Blank blank : blanks) {
            try {
                if (blank.getType() == null) {
                    throw new UnknownWoodException("Неизвестный тип заготовки");
                }
                int blPerMeter = PRODUCT_PER_METER.getOrDefault(blank.getDiameter(), 0);
                int useLength = blank.getLength() / 2;
                int planks = blPerMeter * useLength;
                result.put(blank.getType(), result.getOrDefault(blank.getType(), 0) + planks);
            } catch (UnknownWoodException e) {
                count++;
            }
        }

        if (count > 0) {
            System.out.printf("%d заготовок неизвестного происхождения и были пропущены%n", count);
        }

        return result;

    }

}
