package khan.pet.Service;

import khan.pet.dto.QuantityByTypeDto;
import khan.pet.entity.Board;
import khan.pet.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public List<QuantityByTypeDto> findQuantityByType() {
        return boardRepository.findQuantityByType();
    }

    @Transactional
    public void deleteBoardsByType(String typeName) {
        boardRepository.deleteBoardByWoodTypes_Name(typeName);
    }

    @Transactional
    public Board saveBoard(Board board) {
        return boardRepository.save(board);
    }

}
