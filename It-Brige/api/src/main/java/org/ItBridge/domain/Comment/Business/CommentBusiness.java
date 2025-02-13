package org.ItBridge.domain.Comment.Business;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.db.Post.PostRepository;
import org.ItBridge.db.borad.BoardRepository;
import org.ItBridge.db.user.UserRepository;
import org.ItBridge.domain.Comment.Controller.Model.CommentReqeust;
import org.ItBridge.domain.Comment.Controller.Model.CommentResponse;
import org.ItBridge.domain.Comment.Converter.CommentConverter;
import org.ItBridge.domain.Comment.Service.CommentService;
import org.ItBridge.domain.Post.Service.PostService;
import org.ItBridge.domain.User.Service.UserService;

import java.util.List;

@Business
@RequiredArgsConstructor
public class CommentBusiness {

    private final CommentService commentService;
    private final CommentConverter commentConverter;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public CommentResponse createComment(CommentReqeust commentReqeust) {
        var userId = userRepository.findById(commentReqeust.getUser_id()).orElseThrow(()->new IllegalArgumentException("user gone"));
        var postId = postRepository.findById(commentReqeust.getPost_id()).orElseThrow(()->new IllegalArgumentException("board gone"));
        var commentEntity = commentConverter.toEntity(commentReqeust,userId, postId );

        var saveresponse = commentService.createComment(commentEntity);
        return commentConverter.toResponse(saveresponse);
    }

    public List<CommentResponse> getCommentByPostId(Long post_id){
        var commentEntity = commentService.getCommentByPostId(post_id);

        return commentConverter.toResponse(commentEntity);
    }

}
