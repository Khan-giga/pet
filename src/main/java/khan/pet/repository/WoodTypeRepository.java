package khan.pet.repository;

import khan.pet.entity.WoodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WoodTypeRepository extends JpaRepository<WoodType, Long> {

    Optional<WoodType> findWoodTypeByName(String name);

    void deleteWoodTypeByName(String name);


}
