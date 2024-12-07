package org.ItBridge.db.Lecture;

import org.ItBridge.db.user.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<LectureEntity,Long> {
//    Optional<UserEntity> findFirstByIdOrderByIdDesc(Long id);
    Optional<LectureEntity> findFirstByCategoryOrderByIdDesc(String category);

    List<LectureEntity> findByCategoryIgnoreCase(String category);

    // 모든 강의 가져오기
    List<LectureEntity> findAllBy();

}
