package khan.pet.repository;

import khan.pet.dto.QuantityByTypeDto;
import khan.pet.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    void deleteBoardByWoodTypes_Name(String type);

    @Query("select new khan.pet.dto.QuantityByTypeDto(b.woodTypes.name, count(b)) from Board b group by b.woodTypes.name")
    List<QuantityByTypeDto> findQuantityByType();

}
