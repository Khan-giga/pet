package khan.pet.Service;

import khan.pet.entity.WorkpieceDiameter;
import khan.pet.repository.WorkpieceDiameterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class WorkpiceceDiameterService {
    private final WorkpieceDiameterRepository workpieceDiameterRepository;

    @Transactional(readOnly = true)
    public Optional<WorkpieceDiameter> findByDiameter(Integer diameter) {
        return workpieceDiameterRepository.findWorkpieceDiameterByMilimetersDiameter(diameter);
    }

    @Transactional
    public void updateDiameter(Integer diameter, Integer updateDiameter, Integer updateCount) {
        workpieceDiameterRepository.updateWorkpieceDiameter(diameter, updateDiameter, updateCount);
    }

    @Transactional
    public void updateDiameter(Integer diameter, Integer updateDiameter) {
        workpieceDiameterRepository.updateWorkpieceDiameter(diameter, updateDiameter);
    }

}
