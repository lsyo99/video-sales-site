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
    Optional<LectureEntity> findById(Long id);
    Optional<LectureEntity> findFirstByCategoryOrderByIdDesc(String category);

    List<LectureEntity> findByCategoryIgnoreCase(String category);

    // 모든 강의 가져오기
    List<LectureEntity> findAllBy();

    @Query("SELECT l FROM LectureEntity l WHERE " +
            "LOWER(l.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.tags) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(l.category) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<LectureEntity> searchByKeyword(@Param("keyword") String keyword);
}
