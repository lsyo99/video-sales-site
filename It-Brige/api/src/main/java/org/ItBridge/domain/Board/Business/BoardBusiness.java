package org.ItBridge.domain.Board.Business;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.db.borad.BoardEntity;
import org.ItBridge.db.borad.BoardRepository;
import org.ItBridge.domain.Board.Controller.Model.BoardRequest;
import org.ItBridge.domain.Board.Controller.Model.BoardResponse;
import org.ItBridge.domain.Board.Converter.BoardConverter;
import org.ItBridge.domain.Board.Service.BoardService;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class BoardBusiness {
    private BoardService boardService;
    private BoardConverter boardConverter;
    public List<BoardResponse> getAllBoards() {
        return boardService.getAllBoards();
    }

    public BoardResponse getBoardById(Long id) {
        return boardService.getBoardById(id);
    }

}
