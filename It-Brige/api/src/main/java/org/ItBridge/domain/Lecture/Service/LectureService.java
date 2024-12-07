package org.ItBridge.domain.Lecture.Service;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.Lecture.LectureEntity;
import org.ItBridge.db.Lecture.LectureRepository;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.Lecture.Converter.LectureConverter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureService {
    public final LectureRepository lectureRepository;
    private final LectureConverter lectureConverter;


    public List<LectureEntity> getTop5Lecture(String category) {
        List<LectureEntity> listLecture;

        if ("all".equalsIgnoreCase(category) || "전체".equalsIgnoreCase(category)) {
            // 모든 강의 가져오기
            listLecture = lectureRepository.findAllBy();
        } else {
            // 특정 카테고리 강의 가져오기
            listLecture = lectureRepository.findByCategoryIgnoreCase(category);
        }

        // likes 기준으로 정렬 후 상위 5개 반환
        return listLecture.stream()
                .sorted(Comparator.comparingInt(LectureEntity::getLikes).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
