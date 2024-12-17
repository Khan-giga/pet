package khan.pet.Service;

import khan.pet.dto.request.RequestPartDto;
import khan.pet.dto.response.ResponsePartDto;
import khan.pet.entity.Part;
import khan.pet.repository.PartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class PartService {
    private final Sawmill sawmill;
    private final PartRepository partRepository;

    @Transactional
    public ResponsePartDto savePart(RequestPartDto requestPartDto) {
        Map<String, Long> sawmillResult = sawmill.calculatePlanksForDb(requestPartDto.blanks());
        Part part = partRepository.save(Part.builder()
                .partNumber(requestPartDto.partyNumber())
                .planks(sawmillResult)
                .build());
        return ResponsePartDto.builder()
                .id(part.getId())
                .processResult(part.getPlanks())
                .partNumber(part.getPartNumber())
                .build();
    }

}
