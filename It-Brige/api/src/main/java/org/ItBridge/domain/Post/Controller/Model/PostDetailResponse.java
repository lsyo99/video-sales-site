package org.ItBridge.domain.Post.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ItBridge.domain.Comment.Controller.Model.CommentResponse;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDetailResponse {
    private Long post_id;
    private String title;
    private String body;
    private Long user_id;
    private LocalDateTime createdAt;
    private String user_name;
    private List<CommentResponse> comments;
}
