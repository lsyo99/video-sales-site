package org.ItBridge.domain.Comment.Controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ItBridge.domain.Comment.Business.CommentBusiness;
import org.ItBridge.domain.Comment.Controller.Model.CommentReqeust;
import org.ItBridge.domain.Comment.Controller.Model.CommentResponse;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/comments")
public class CommentController {
    private final CommentBusiness commentBusiness;

    @PostMapping("/create/comment")
    public CommentResponse createComment(
            @RequestBody CommentReqeust commentReqeust
            ){

        return commentBusiness.createComment(commentReqeust);
    }
}
