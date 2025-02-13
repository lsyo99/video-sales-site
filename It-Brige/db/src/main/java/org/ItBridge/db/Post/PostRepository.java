package org.ItBridge.db.Post;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long> {

    Page<PostEntity> findByBoard_Id(Long boardId, Pageable pageable);
    @Query("SELECT p FROM PostEntity p JOIN FETCH p.user WHERE p.id = :postId")
    Optional<PostEntity> findPostWithUser(@Param("postId") Long postId);
    Optional<PostEntity> findById(Long id);
}
