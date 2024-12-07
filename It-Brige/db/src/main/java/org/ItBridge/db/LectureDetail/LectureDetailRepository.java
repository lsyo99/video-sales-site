package org.ItBridge.db.LectureDetail;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureDetailRepository extends JpaRepository<LectureDetailEntity, Long> {

    List<LectureDetailEntity> findAllByLectureIdOrderBySortImg(Long lecture_id);

}
