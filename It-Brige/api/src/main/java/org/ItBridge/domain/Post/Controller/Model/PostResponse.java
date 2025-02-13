package org.ItBridge.domain.Post.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String body;
    private Long user_id;
    private Long Board_id;
    private LocalDateTime created_at;
    private String user_name;

}
