package org.ItBridge.domain.Comment.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse {

    private Long id;
    private Long post_id;
    private Long user_id;
    private String user_name;
    private String body;
    private LocalDateTime created_at;
}
