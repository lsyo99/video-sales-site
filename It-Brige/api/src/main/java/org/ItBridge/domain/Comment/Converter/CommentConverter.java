package org.ItBridge.domain.Comment.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.Comment.CommentEntity;
import org.ItBridge.db.Lecture.LectureEntity;
import org.ItBridge.db.Post.PostEntity;
import org.ItBridge.db.user.UserEntity;
import org.ItBridge.domain.Comment.Controller.Model.CommentReqeust;
import org.ItBridge.domain.Comment.Controller.Model.CommentResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
@RequiredArgsConstructor
public class CommentConverter {

    public CommentResponse toResponse(CommentEntity commentEntity){
        return Optional.ofNullable(commentEntity).map(it->{
            return CommentResponse.builder()
                    .id(it.getId())
                    .body(it.getBody())
                    .user_id(it.getUser().getId())
                    .post_id(it.getPost().getId())
                    .created_at(it.getCreatedAt())
                    .build();
        }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"Comment response is null"));

    }
    public List<CommentResponse> toResponse(List<CommentEntity> commentEntities) { // ✅ List 변환
        return Optional.ofNullable(commentEntities)
                .map(comments -> comments.stream()
                        .map(comment -> CommentResponse.builder()
                                .id(comment.getId())
                                .body(comment.getBody())
                                .user_id(comment.getUser().getId())
                                .post_id(comment.getPost().getId())
                                .created_at(comment.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "Comment response is null"));
    }
    public CommentEntity toEntity(CommentReqeust commentReqeust, UserEntity user_id, PostEntity post_id){

        return Optional.ofNullable(commentReqeust).map(it -> {
            return CommentEntity.builder()
                    .body(commentReqeust.getComment())
                    .post(post_id)
                    .user(user_id)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
        }).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"commet Reqeust is ull"));
    }

}
