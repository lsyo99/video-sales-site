package org.ItBridge.domain.Post.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCreateRequest {
    private Long board_id;
    private String title;
    private String body;
    private Long user_id;

}
