package khan.pet.dto;

import lombok.Builder;

@Builder
public record QuantityByTypeDto(String type, Long quantity) {
}
