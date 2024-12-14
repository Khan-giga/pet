package khan.pet.Service;

import khan.pet.entity.Blank;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Sawmill {

    private static final Map<Integer, Integer> PRODUCT_PER_METER = new HashMap<>();

    static {
        PRODUCT_PER_METER.put(200, 3);
        PRODUCT_PER_METER.put(500, 7);
        PRODUCT_PER_METER.put(700, 12);
    }

    public static Map<String, Integer> boardCalculator(List<Blank> blanks) {

        Map<String, Integer> boardsByType = new HashMap<>();
        int unknownBoardType = 0;

        for (Blank blank : blanks) {
            if (blank.getType() == null) {
                unknownBoardType++;
                continue;
            }
            int planks = getPlanks(blank);
            boardsByType.put(blank.getType(), boardsByType.getOrDefault(blank.getType(), 0) + planks);
        }

        if (unknownBoardType > 0) {
            System.out.printf("%d заготовок неизвестного происхождения и были пропущены%n", unknownBoardType);
        }

        return boardsByType;

    }

    private static int getPlanks(Blank blank) {
        int blPerMeter = Optional.ofNullable(PRODUCT_PER_METER.get(blank.getDiameter()))
                .orElseGet(() -> getClosestProductForDiameter(blank.getDiameter()));
        int useLength = blank.getLength() / 2;
        return blPerMeter * useLength;
    }

    private static Integer getClosestProductForDiameter(int diameter) {
        if (diameter > 0 && diameter < 300) {
            return PRODUCT_PER_METER.get(200);
        } else if (diameter >= 600) {
            return PRODUCT_PER_METER.get(700);
        } else {
            return PRODUCT_PER_METER.get(500);
        }
    }

}
