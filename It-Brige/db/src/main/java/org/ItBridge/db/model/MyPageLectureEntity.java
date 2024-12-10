package org.ItBridge.db.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MyPageLectureEntity {
    private Long paymentId;
    private Long lectureId;
    private String thumbnailUrl;
    private String title;
    private String category;
    private BigDecimal firstPrice;
    private Integer salse;
    private BigDecimal account;
    private LocalDateTime payedDate;
    private String payedMethod;
}

