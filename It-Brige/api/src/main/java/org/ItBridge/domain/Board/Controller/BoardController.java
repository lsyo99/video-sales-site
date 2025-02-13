package org.ItBridge.domain.Board.Controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.domain.Board.Business.BoardBusiness;
import org.ItBridge.domain.Board.Controller.Model.BoardRequest;
import org.ItBridge.domain.Board.Controller.Model.BoardResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/open-api/boards")
@RestController
@RequiredArgsConstructor
public class BoardController {
    private BoardBusiness boardBusiness;

    @GetMapping
    public List<BoardResponse> getAllBoards() {
        return boardBusiness.getAllBoards();
    }

    @GetMapping("/{id}")
    public BoardResponse getBoardById(@PathVariable Long id) {
        return boardBusiness.getBoardById(id);

    }
}
