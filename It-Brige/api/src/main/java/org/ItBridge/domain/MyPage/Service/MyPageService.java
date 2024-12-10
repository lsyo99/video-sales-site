package org.ItBridge.domain.MyPage.Service;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.ItBridge.db.model.MyPageLectureConverter;
import org.ItBridge.db.model.MyPageLectureEntity;
import org.ItBridge.db.mypage.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyPageService {
    private final PaymentRepository paymentRepository;
    private final MyPageLectureConverter myPageLectureConverter;
    public List<MyPageLectureEntity> getMyLecture(Long user_id){
        var tupleLecture = paymentRepository.findMyLecturesByUserId(user_id);
        List<MyPageLectureEntity> lectures = new ArrayList<>();

        for (Tuple tuple : tupleLecture) {
            lectures.add(MyPageLectureConverter.toEntity(tuple));
        }
        return lectures;
    }
}
