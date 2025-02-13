package org.ItBridge.domain.Lecture.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.Lecture.LectureEntity;
import org.ItBridge.domain.Lecture.Controller.model.LectureRequest;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.Lecture.Controller.model.LectureSaveReqeust;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
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
                            .rank(lectureEntity.getRanking())
                            .category(lectureEntity.getCategory())
                            .thumbnailUrl(lectureEntity.getThumbnailUrl())
                            .rank(lectureEntity.getRanking())
                            .build();
                }).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"lecture Null"));
    }

    public LectureEntity tosvaeEntity(String thumbnailUrl, LectureSaveReqeust lectureSaveReqeust) {
        return Optional.ofNullable(lectureSaveReqeust)
                .map(it->{
                    return LectureEntity.builder()
                            .sales(lectureSaveReqeust.getSales())
                            .tags(lectureSaveReqeust.getTagsAsJson())
                            .price(lectureSaveReqeust.getPrice())
                            .title(lectureSaveReqeust.getTitle())
                            .thumbnailUrl(thumbnailUrl)
                            .category(lectureSaveReqeust.getCategory())
                            .uploadAt(LocalDateTime.now())
                            .build();

                }).orElseThrow(()-> new ApiException(ErrorCode.SERVER_ERROR,"저장 안됨"));

    }
}
