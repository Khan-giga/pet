package khan.pet.dto.response;

import lombok.Builder;

import java.util.Map;

@Builder
public record ResponsePartDto(Long id, Long partNumber, Map<String, Long> processResult) {
}
