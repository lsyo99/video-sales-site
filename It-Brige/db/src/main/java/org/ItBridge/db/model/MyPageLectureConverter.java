package org.ItBridge.db.model;

import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor

    public class MyPageLectureConverter {
        public static MyPageLectureEntity toEntity(Tuple tuple) {
            MyPageLectureEntity entity = new MyPageLectureEntity();

            // Tuple에서 각 필드를 추출하여 entity에 세팅
            entity.setPaymentId(tuple.get(0, Long.class));
            entity.setLectureId(tuple.get(1, Long.class));
            entity.setThumbnailUrl(tuple.get(2, String.class));
            entity.setTitle(tuple.get(3, String.class));
            entity.setCategory(tuple.get(4, String.class));
            entity.setFirst_price(tuple.get(5, BigDecimal.class));
            entity.setSalse(tuple.get(6, Integer.class)); // Sales는 Integer로 처리
            entity.setAccount(tuple.get(7, BigDecimal.class));

            // Timestamp를 LocalDateTime으로 변환
            Timestamp timestamp = tuple.get(8, Timestamp.class);  // Timestamp 객체 추출
            if (timestamp != null) {
                entity.setPayed_date(timestamp.toLocalDateTime());  // 변환 작업
            }

            entity.setPayed_method(tuple.get(9, String.class));

            return entity;
        }
    }
