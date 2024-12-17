package khan.pet.repository;

import khan.pet.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartRepository extends JpaRepository<Part, Long> {

    Part findPartByPartNumber(Long partNumber);

    void deletePartByPartNumber(Long partNumber);

}
