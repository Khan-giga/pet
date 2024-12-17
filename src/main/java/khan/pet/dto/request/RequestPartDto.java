package khan.pet.dto.request;

import khan.pet.dto.Blank;
import lombok.Builder;

import java.util.List;

@Builder
public record RequestPartDto(Long partyNumber, List<Blank> blanks) {
}
