package org.ItBridge.domain.Post.Controller.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostRequest {
    @NotBlank(message = "Title is required.")
    private String title;
    @NotBlank(message = "Body is required.")
    private String body;
    @NotNull(message = "User ID is required.")
    private Long user_id;
    @NotNull(message = "Board ID is required.")
    private Long board_id;

}
