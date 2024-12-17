package khan.pet.controller;

import khan.pet.Service.PartService;
import khan.pet.dto.request.RequestPartDto;
import khan.pet.dto.response.ResponsePartDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/sawmill")
@RequiredArgsConstructor
public class SawmillController {
    private final PartService partService;

    @PostMapping
    public ResponseEntity<ResponsePartDto> processBatch(@RequestBody RequestPartDto requestPartDto) {
        return ok(partService.savePart(requestPartDto));
    }

}
