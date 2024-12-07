package org.ItBridge.domain.LectureDetail.Service;

import lombok.RequiredArgsConstructor;
import org.ItBridge.db.Lecture.LectureRepository;
import org.ItBridge.db.LectureDetail.LectureDetailEntity;
import org.ItBridge.db.LectureDetail.LectureDetailRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LectureDetailService {
    private final LectureDetailRepository lectureDetailRepository;

    public List<LectureDetailEntity> getLectureImg(Long lectureId){

        return lectureDetailRepository.findAllByLectureIdOrderBySortImg(lectureId);
    }

}
