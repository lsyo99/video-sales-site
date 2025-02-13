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

import java.time.LocalDateTime;
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

    public List<LectureEntity> searchCourses(String keyword) {
        return lectureRepository.searchByKeyword(keyword);
    }


    public List<LectureEntity> getNewLecture(String category) {
        List<LectureEntity> listLecture;

        if ("all".equalsIgnoreCase(category) || "전체".equalsIgnoreCase(category)) {
            // 모든 강의 가져오기
            listLecture = lectureRepository.findAllBy();
        } else {
            // 특정 카테고리 강의 가져오기
            listLecture = lectureRepository.findByCategoryIgnoreCase(category);
        }
        // 현재 날짜 기준으로 40일 이내 강의 필터링
        LocalDateTime fortyDaysAgo = LocalDateTime.now().minusDays(40);

        return listLecture.stream()
                .filter(lecture -> {
                    boolean isWithin40Days = lecture.getUploadAt() != null && lecture.getUploadAt().isAfter(fortyDaysAgo);
                    if (!isWithin40Days) {
                        System.out.println("제외된 강의 (40일 이전): " + lecture.getTitle() + ", 업로드 날짜: " + lecture.getUploadAt());
                    }
                    return isWithin40Days;
                })
                .sorted(Comparator.comparing(LectureEntity::getLikes, Comparator.reverseOrder()) // 인기 순으로 정렬
                        .thenComparing(LectureEntity::getUploadAt, Comparator.reverseOrder())) // 업로드 날짜로 정렬
                .limit(5) // 상위 5개 선택
                .peek(lecture -> System.out.println("선택된 강의: " + lecture.getTitle() + ", 좋아요: " + lecture.getLikes() + ", 업로드 날짜: " + lecture.getUploadAt()))
                .collect(Collectors.toList());
    }

    public List<LectureEntity> getLectureHeader(String type) {
        List<LectureEntity> listLecture;

        switch (type.toLowerCase()) {
            case "new": {
                listLecture = lectureRepository.findAllBy(); // 모든 강의 가져오기
                LocalDateTime fortyDaysAgo = LocalDateTime.now().minusDays(40);

                return listLecture.stream()
                        .filter(lecture -> lecture.getUploadAt() != null && lecture.getUploadAt().isAfter(fortyDaysAgo)) // 40일 이내 강의 필터링
                        .sorted(Comparator.comparing(LectureEntity::getLikes, Comparator.reverseOrder())) // 좋아요 순 정렬
                        .collect(Collectors.toList());
            }

            case "discount": {
                listLecture = lectureRepository.findAllBy(); // 모든 강의 가져오기

                return listLecture.stream()
                        .filter(lecture -> lecture.getSales() != null) // 할인 정보가 있는 강의 필터링
                        .sorted(Comparator.comparing(LectureEntity::getSales).reversed()) // 할인율 높은 순 정렬
                        .collect(Collectors.toList());
            }

            default:
                throw new IllegalArgumentException("Invalid lecture type: " + type);
        }
    }

    public LectureEntity getLecture(Long lectureId) {
        return lectureRepository.findById(lectureId).orElseThrow(()->new ApiException(ErrorCode.NULL_POINT,"강의를 찾을 수 없습니다."));

    }

    public LectureEntity saveLEcture(LectureEntity entity) {
        return lectureRepository.save(entity);
    }
}
