package khan.pet.Service;

import khan.pet.entity.WoodType;
import khan.pet.repository.WoodTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WoodTypeService {
    private final WoodTypeRepository woodTypeRepository;

    @Transactional(readOnly = true)
    public Optional<WoodType> findByName(String name) {
        return woodTypeRepository.findWoodTypeByName(name);
    }

    @Transactional
    public void deleteByName(String name) {
        woodTypeRepository.deleteWoodTypeByName(name);
    }

}
