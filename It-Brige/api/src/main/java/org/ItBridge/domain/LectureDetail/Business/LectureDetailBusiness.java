package org.ItBridge.domain.LectureDetail.Business;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.db.Lecture.LectureEntity;
import org.ItBridge.db.LectureDetail.LectureDetailEntity;
import org.ItBridge.domain.LectureDetail.Controller.model.LectureDetailResponse;
import org.ItBridge.domain.LectureDetail.Converter.LectureDetailConverter;
import org.ItBridge.domain.LectureDetail.Service.LectureDetailService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class LectureDetailBusiness {
    private final LectureDetailService lectureDetailService;
    private final LectureDetailConverter lectureDetailConverter;
    public List<LectureDetailResponse> getLectureImages(Long lectureId) {
        List<LectureDetailEntity> listLectureDetailEntity = lectureDetailService.getLectureImg(lectureId);
        return listLectureDetailEntity.stream()
                .map(it->{
                    return lectureDetailConverter.toResponse(it);
                }).collect(Collectors.toList());
    }
}
