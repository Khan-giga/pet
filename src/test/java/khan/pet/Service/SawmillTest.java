package khan.pet.Service;

import khan.pet.dto.QuantityByTypeDto;
import khan.pet.dto.Blank;
import khan.pet.entity.WoodType;
import khan.pet.entity.Workpiece;
import khan.pet.entity.WorkpieceDiameter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SawmillTest {

    private static List<Blank> blanks;

    @Mock
    private BoardService boardService;
    @Mock
    private WorkpieceService workpieceService;
    @Mock
    private WoodTypeService woodTypeService;
    @Mock
    private WorkpiceceDiameterService workpiceceDiameterService;
    @InjectMocks
    private Sawmill sawmill;

    @BeforeAll
    static void setUp() {
        blanks = new ArrayList<>(Arrays.asList(
                new Blank("Дуб", 500, 14L),
                new Blank("Дуб", 200, 14L),
                new Blank("Ель", 500, 7L),
                new Blank("Сосна", 500, 10L)));
    }

    @Test
    void findQuantityByType() {
        WoodType woodType1 = WoodType.builder().id(1L).name("Дуб").build();
        WoodType woodType2 = WoodType.builder().id(2L).name("Сосна").build();
        WoodType woodType3 = WoodType.builder().id(3L).name("Ель").build();

        WorkpieceDiameter workpieceDiameter1 = WorkpieceDiameter.builder()
                .id(1L).milimetersDiameter(200).boardCount(3).build();
        WorkpieceDiameter workpieceDiameter2 = WorkpieceDiameter.builder()
                .id(2L).milimetersDiameter(500).boardCount(7).build();
        WorkpieceDiameter workpieceDiameter3 = WorkpieceDiameter.builder()
                .id(3L).milimetersDiameter(700).boardCount(12).build();

        Workpiece workpiece = Workpiece.builder()
                .workpieceDiameter(workpieceDiameter2).woodTypes(woodType1).metersLength(14L).build();
        Workpiece workpiece1 = Workpiece.builder()
                .workpieceDiameter(workpieceDiameter1).woodTypes(woodType1).metersLength(14L).build();
        Workpiece workpiece2 = Workpiece.builder()
                .workpieceDiameter(workpieceDiameter2).woodTypes(woodType2).metersLength(10L).build();
        Workpiece workpiece3 = Workpiece.builder()
                .workpieceDiameter(workpieceDiameter3).woodTypes(woodType3).metersLength(7L).build();

        QuantityByTypeDto quantityByTypeDto1 = QuantityByTypeDto.builder()
                .type(woodType1.getName()).quantity(98L).build();
        QuantityByTypeDto quantityByTypeDto2 = QuantityByTypeDto.builder()
                .type(woodType2.getName()).quantity(35L).build();
        QuantityByTypeDto quantityByTypeDto3 = QuantityByTypeDto.builder()
                .type(woodType3.getName()).quantity(21L).build();

        when(woodTypeService.findByName("Дуб")).thenReturn(Optional.of(woodType1));
        when(woodTypeService.findByName("Сосна")).thenReturn(Optional.of(woodType2));
        when(woodTypeService.findByName("Ель")).thenReturn(Optional.of(woodType3));
        when(workpiceceDiameterService.findByDiameter(200)).thenReturn(Optional.of(workpieceDiameter1));
        when(workpiceceDiameterService.findByDiameter(500)).thenReturn(Optional.of(workpieceDiameter2));
        when(workpiceceDiameterService.findByDiameter(500)).thenReturn(Optional.of(workpieceDiameter2));
        when(workpieceService.save(any(Workpiece.class))).thenReturn(workpiece, workpiece1, workpiece2, workpiece3);
        when(boardService.findQuantityByType())
                .thenReturn(List.of(quantityByTypeDto1, quantityByTypeDto2, quantityByTypeDto3));

        Map<String, Long> map = sawmill.calculatePlanksForDb(blanks);

        assertNotNull(map);
        assertEquals(98, map.get("Дуб"));
        assertEquals(35, map.get("Сосна"));
        assertEquals(21, map.get("Ель"));

        verify(woodTypeService, times(2)).findByName("Дуб");
        verify(woodTypeService).findByName("Сосна");
        verify(woodTypeService).findByName("Ель");
        verify(workpiceceDiameterService).findByDiameter(200);
        verify(workpiceceDiameterService, times(3)).findByDiameter(500);
        verify(workpieceService, times(4)).save(any(Workpiece.class));
        verify(boardService).findQuantityByType();

    }

}