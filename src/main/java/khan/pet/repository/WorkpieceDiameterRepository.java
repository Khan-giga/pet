package khan.pet.repository;

import khan.pet.entity.WorkpieceDiameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WorkpieceDiameterRepository extends JpaRepository<WorkpieceDiameter, Long> {

    Optional<WorkpieceDiameter> findWorkpieceDiameterByMilimetersDiameter(Integer diameter);

    @Modifying
    @Query("""
            update WorkpieceDiameter wd
            set wd.milimetersDiameter = :updatedDiameter, wd.boardCount = :updatedBoardCount
            where wd.milimetersDiameter = :diameter""")
    void updateWorkpieceDiameter(@Param("diameter") Integer diameter,
                                 @Param("updatedDiameter") Integer updatedDiameter,
                                 @Param("updatedBoardCount") Integer updatedBoardCount);

    @Modifying
    @Query("""
            update WorkpieceDiameter wd
            set wd.milimetersDiameter = :updatedDiameter
            where wd.milimetersDiameter = :diameter""")
    void updateWorkpieceDiameter(@Param("diameter") Integer diameter,
                                 @Param("updatedDiameter") Integer updatedDiameter);

}
