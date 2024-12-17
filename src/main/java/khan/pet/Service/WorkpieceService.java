package khan.pet.Service;

import khan.pet.entity.Workpiece;
import khan.pet.repository.WorkpieceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WorkpieceService {
    private final WorkpieceRepository workpieceRepository;

    @Transactional
    public Workpiece save(Workpiece workpiece) {
        return workpieceRepository.save(workpiece);
    }

}
