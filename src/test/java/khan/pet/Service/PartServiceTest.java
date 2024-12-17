package khan.pet.Service;

import khan.pet.dto.Blank;
import khan.pet.dto.request.RequestPartDto;
import khan.pet.dto.response.ResponsePartDto;
import khan.pet.entity.Part;
import khan.pet.repository.PartRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PartServiceTest {

    @Mock
    private Sawmill sawmill;
    @Mock
    private PartRepository partRepository;
    @InjectMocks
    private PartService partService;

    static List<Blank> blanks;

    @BeforeAll
    static void setUp() {
        blanks = new ArrayList<>(Arrays.asList(
                new Blank("Дуб", 500, 14L),
                new Blank("Дуб", 200, 14L),
                new Blank("Ель", 500, 7L),
                new Blank("Сосна", 500, 10L)));
    }

    @Test
    void testSaveNewPart() {
        Map<String, Long> result = new HashMap<>();
        result.put("Дуб", 98L);
        result.put("Сосна", 35L);
        result.put("Ель", 21L);

        RequestPartDto requestPartDto = RequestPartDto.builder().blanks(blanks).partyNumber(1L) .build();
        Part part = Part.builder().planks(result).partNumber(requestPartDto.partyNumber()).build();

        when(sawmill.calculatePlanksForDb(requestPartDto.blanks())).thenReturn(result);
        when(partRepository.save(any(Part.class))).thenReturn(Part.builder()
                .id(1L)
                .partNumber(part.getPartNumber())
                .planks(part.getPlanks())
                .build());

        ResponsePartDto responsePartDto = partService.savePart(requestPartDto);

        assertNotNull(responsePartDto);
        assertEquals(1L, responsePartDto.partNumber());
        assertEquals(result, responsePartDto.processResult());
        assertEquals(98, responsePartDto.processResult().get("Дуб"));
        assertEquals(35, responsePartDto.processResult().get("Сосна"));
        assertEquals(21, responsePartDto.processResult().get("Ель"));

        verify(sawmill).calculatePlanksForDb(requestPartDto.blanks());
        verify(partRepository).save(any(Part.class));

    }

}