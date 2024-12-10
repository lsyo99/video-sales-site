package org.ItBridge.domain.MyPage.Controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyPageLectureResponse {

        private Long payment_id;
        private Long lecture_id;
        private String thumbnail_url;
        private String title;
        private String category;
        private BigDecimal first_price;
        private Integer salse;
        private BigDecimal account;
        private LocalDateTime payed_data;
        private String pay_method;
    }

