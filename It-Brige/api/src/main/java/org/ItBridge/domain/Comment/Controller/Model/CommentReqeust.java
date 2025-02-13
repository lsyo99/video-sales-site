package org.ItBridge.domain.Comment.Controller.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentReqeust {
    @NotBlank
    private String comment;
    @NotNull
    private Long user_id;
    @NotNull
    private Long post_id;
}
