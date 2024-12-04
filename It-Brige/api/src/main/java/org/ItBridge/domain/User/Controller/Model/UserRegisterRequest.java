package org.ItBridge.domain.User.Controller.Model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {
    @NotBlank
    private String name;
    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String phone;


    private LocalDateTime birth;

}