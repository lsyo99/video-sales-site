package org.ItBridge.domain.User.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public class UserResponse {

        private Long id;
        private String name;

        private String email;

        private String address;



    }


