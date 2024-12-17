package khan.pet.Service;

import jakarta.transaction.Transactional;
import khan.pet.dto.QuantityByTypeDto;
import khan.pet.dto.Blank;
import khan.pet.entity.Board;
import khan.pet.entity.WoodType;
import khan.pet.entity.Workpiece;
import khan.pet.entity.WorkpieceDiameter;
import khan.pet.exception.UnknownWoodException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class Sawmill {

    private final WoodTypeService woodTypeService;
    private final BoardService boardService;
    private final WorkpieceService workpieceService;
    private final WorkpiceceDiameterService workpiceceDiameterService;

    private static final Map<Integer, Integer> PRODUCT_PER_METER = new HashMap<>();

    static {
        PRODUCT_PER_METER.put(200, 3);
        PRODUCT_PER_METER.put(500, 7);
        PRODUCT_PER_METER.put(700, 12);
    }


    @Transactional
    public Map<String, Long> calculatePlanksForDb(List<Blank> blanks) {
        if (blanks == null || blanks.isEmpty()) {
            return new HashMap<>();
        }

        for (Blank blank : blanks) {
            WoodType woodType = woodTypeService.findByName(blank.getType()).orElseThrow(UnknownWoodException::new);
            WorkpieceDiameter workpieceDiameter = workpiceceDiameterService
                    .findByDiameter(blank.getDiameter()).orElseThrow();
            Workpiece workpiece = workpieceService
                    .save(Workpiece.builder()
                            .woodTypes(woodType)
                            .workpieceDiameter(workpieceDiameter)
                            .metersLength(blank.getLength()).build());

            long planks = getPlanks(blank, workpieceDiameter);

            for (int i = 0; i < planks; i++) {
                boardService.saveBoard(Board.builder().woodTypes(woodType).workpieces(workpiece).build());
            }
        }

        return boardService.findQuantityByType().stream()
                .collect(Collectors.toMap(
                        QuantityByTypeDto::type,
                        QuantityByTypeDto::quantity,
                        Long::sum
                ));

    }

    private static long getPlanks(Blank blank, WorkpieceDiameter workpieceDiameter) {
        int blPerMeter = workpieceDiameter.getBoardCount();
        long useLength = blank.getLength() / 2;
        return blPerMeter * useLength;
    }

    public static Map<String, Integer> calculatePlanks(List<Blank> blanks) {

        Map<String, Integer> result = new HashMap<>();

        for (Blank blank : blanks) {
            int blPerMeter = PRODUCT_PER_METER.getOrDefault(blank.getDiameter(), 0);
            long useLength = blank.getLength() / 2;
            int planks = Math.toIntExact(blPerMeter * useLength);
            result.put(blank.getType(), result.getOrDefault(blank.getType(), 0) + planks);
        }

        return result;

    }

}
