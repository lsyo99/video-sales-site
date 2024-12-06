package org.ItBridge.domain.User.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    private LocalDateTime birthday;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;
}
