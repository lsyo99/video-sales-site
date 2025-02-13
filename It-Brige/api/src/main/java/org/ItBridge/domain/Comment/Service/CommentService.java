package org.ItBridge.domain.Comment.Service;

import lombok.RequiredArgsConstructor;
import org.ItBridge.db.Comment.CommentEntity;
import org.ItBridge.db.Comment.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentEntity createComment(CommentEntity commentEntity) {
        return commentRepository.save(commentEntity);
    }


    public List<CommentEntity> getCommentByPostId(Long postId) {
        return commentRepository.findAllByPostIdOrderByCreatedAt(postId);
    }
}
