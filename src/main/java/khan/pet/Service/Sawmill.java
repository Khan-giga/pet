package khan.pet.Service;

import khan.pet.dto.request.Blank;
import khan.pet.entity.Board;
import khan.pet.entity.WoodType;
import khan.pet.entity.Workpiece;
import khan.pet.entity.WorkpieceDiameter;
import khan.pet.exception.UnknownWoodException;
import khan.pet.repository.BoardRepository;
import khan.pet.repository.WoodTypeRepository;
import khan.pet.repository.WorkpieceDiameterRepository;
import khan.pet.repository.WorkpieceRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sawmill {

    private static final WoodTypeRepository woodTypeRepository = new WoodTypeRepository();
    private static final BoardRepository boardRepository = new BoardRepository();
    private static final WorkpieceRepository workpieceRepository = new WorkpieceRepository();
    private static final WorkpieceDiameterRepository workpieceDiameterRepository = new WorkpieceDiameterRepository();

    private static final Map<Integer, Integer> PRODUCT_PER_METER = new HashMap<>();

    static {
        PRODUCT_PER_METER.put(200, 3);
        PRODUCT_PER_METER.put(500, 7);
        PRODUCT_PER_METER.put(700, 12);
    }

    public static Map<String, Long> calculatePlanksForDb(List<Blank> blanks) {
        if (blanks == null || blanks.isEmpty()) {
            return new HashMap<>();
        }

        for (Blank blank : blanks) {
            WoodType woodType = woodTypeRepository.findWoodTypeByName(blank.getType()).orElseThrow(UnknownWoodException::new);
            WorkpieceDiameter workpieceDiameter = workpieceDiameterRepository
                    .findWorkpieceDiameterByDiameter(blank.getDiameter()).orElseThrow();
            Workpiece workpiece = workpieceRepository
                    .saveWorkpiece(woodType, workpieceDiameter, blank.getLength());

            long planks = getPlanks(blank, workpieceDiameter);

            for (int i = 0; i < planks; i++) {
                boardRepository.saveBoard(Board.builder().woodTypes(woodType).workpieces(workpiece).build());
            }
        }

        return boardRepository.findQuantityByType();

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
