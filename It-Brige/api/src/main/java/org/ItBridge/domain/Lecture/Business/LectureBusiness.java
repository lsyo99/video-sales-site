package org.ItBridge.domain.Lecture.Business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.domain.Lecture.Controller.model.LectureRequest;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.Lecture.Converter.LectureConverter;
import org.ItBridge.domain.Lecture.Service.LectureService;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
@Slf4j
public class LectureBusiness {
    private final LectureConverter lectureConverter;
    private final LectureService lectureService;

    public List<LectureResponse> getBestLectures(String category) {
        // 상위 5개 강의 데이터를 가져와 변환
        var listEntity = lectureService.getTop5Lecture(category);
        return listEntity.stream()
                .map(lectureConverter::toResponse)
                .collect(Collectors.toList());
    }
}
