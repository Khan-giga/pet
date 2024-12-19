package khan.pet.Service;

import khan.pet.entity.FileStatus;
import khan.pet.repository.FileStatusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FileStatusService {
    private final FileStatusRepository fileStatusRepository;

    @Transactional
    public FileStatus saveFileStatus(FileStatus fileStatus) {
        return fileStatusRepository.save(fileStatus);
    }

}
