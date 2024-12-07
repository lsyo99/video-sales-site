package org.ItBridge.domain.Lecture.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.Lecture.LectureEntity;
import org.ItBridge.domain.Lecture.Controller.model.LectureRequest;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class LectureConverter {
    public LectureEntity toEntity(LectureRequest lectureRequest){
        return Optional.ofNullable(lectureRequest)
                .map(it->{
                    return LectureEntity.builder()
                            .category(lectureRequest.getCategory()).build();

                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"request lecture Null"));
    }

    public LectureResponse toResponse(LectureEntity lectureEntity)
    {
        return Optional.ofNullable(lectureEntity)
                .map(it->{
                    return LectureResponse.builder()
                            .id(lectureEntity.getId())
                            .sales(lectureEntity.getSales())
                            .tags(lectureEntity.getTags())
                            .likes(lectureEntity.getLikes())
                            .title(lectureEntity.getTitle())
                            .price(lectureEntity.getPrice())
                            .rank(lectureEntity.getRank())
                            .category(lectureEntity.getCategory())
                            .thumbnailUrl(lectureEntity.getThumbnailUrl())
                            .rank(lectureEntity.getRank())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"lecture Null"));
    }
}
