package org.ItBridge.db.mypage;

import jakarta.persistence.Tuple;
import org.ItBridge.db.model.MyPageLectureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentEntity,Long> {

    @Query(value = "SELECT " +
            "p.id AS paymentId, " +
            "p.lecture_id AS lectureId, " +
            "l.thumbnail_url AS thumbnail_url, " +
            "l.title AS title, " +
            "l.category AS category, " +
            "p.firstPrice AS firstPrice, " +
            "p.salse AS salse, " +
            "p.account AS account, " +
            "p.payedDate AS payedDate, " +
            "p.payedMethod AS payedMethod " +
            "FROM payment p " +
            "JOIN lecture l ON p.lecture_id = l.id " +
            "WHERE p.user_id = :userId", nativeQuery = true)
    List<Tuple> findMyLecturesByUserId(@Param("userId") Long userId);
}
