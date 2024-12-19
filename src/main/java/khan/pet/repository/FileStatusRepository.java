package khan.pet.repository;

import khan.pet.entity.FileStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStatusRepository extends JpaRepository<FileStatus, Long> {
}
