package org.ItBridge.domain.Board.Converter;

import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.db.borad.BoardEntity;
import org.ItBridge.domain.Board.Controller.Model.BoardRequest;
import org.ItBridge.domain.Board.Controller.Model.BoardResponse;

@Converter
public class BoardConverter {
    public BoardEntity toEntity(BoardRequest request) {
        return BoardEntity.builder()
                .name(request.getName())
                .build();
    }

    public BoardResponse toResponse(BoardEntity entity) {
        BoardResponse response = new BoardResponse();
        response.setId(entity.getId());
        response.setName(entity.getName());
        return response;
    }

}
