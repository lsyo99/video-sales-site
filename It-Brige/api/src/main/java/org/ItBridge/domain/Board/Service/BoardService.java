package org.ItBridge.domain.Board.Service;

import lombok.RequiredArgsConstructor;
import org.ItBridge.db.borad.BoardEntity;
import org.ItBridge.db.borad.BoardRepository;
import org.ItBridge.domain.Board.Controller.Model.BoardResponse;
import org.ItBridge.domain.Board.Converter.BoardConverter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardConverter boardConverter;
    public List<BoardResponse> getAllBoards(){
        return boardRepository.findAll().stream()
                .map(boardConverter::toResponse)
                .collect(Collectors.toList());
    }


    public BoardResponse getBoardById(Long id) {
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Board not found"));
        return boardConverter.toResponse(board);
    }
}
