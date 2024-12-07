package org.ItBridge.domain.Lecture.Controller.model;

import jakarta.persistence.Column;
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
public class LectureResponse {

    private Long id;

    private String title;


    private String category;


    private BigDecimal price;

    private Long sales;

    private Integer likes;


    private String thumbnailUrl;


    private String tags;


    private LocalDateTime uploadAt;
    //TODO 결제하고 payment해서 팔린 수만큼 할 것이기 때문에 나중에 삭제

    private Integer rank;
}
