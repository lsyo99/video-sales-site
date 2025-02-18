package org.ItBridge.db.video;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoRepository extends JpaRepository<VideoEntity, Long > {
    List<VideoEntity> findAllByLectureIdOrderBySortId(Long lectureId);
//
//    List<VideoEntity> findAllByLectureIdOrderBySortId(Long Lecture_id);
}
