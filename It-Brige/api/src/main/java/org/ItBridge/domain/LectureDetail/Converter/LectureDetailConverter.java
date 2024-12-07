package org.ItBridge.domain.LectureDetail.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.api.Api;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.LectureDetail.LectureDetailEntity;
import org.ItBridge.domain.LectureDetail.Controller.model.LectureDetailResponse;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class LectureDetailConverter {
    public LectureDetailResponse toResponse(LectureDetailEntity lectureDetailEntity){
        return Optional.ofNullable(lectureDetailEntity).map(
                it-> {
                    return LectureDetailResponse.builder()
                            .id(it.getId())
                            .lecture_id(it.getLecture().getId())
                            .url(it.getUrl())
                            .sort_img(it.getSortImg())
                            .build();
                }
        ).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"LecutureDetailEntity is null"));
    }
}
