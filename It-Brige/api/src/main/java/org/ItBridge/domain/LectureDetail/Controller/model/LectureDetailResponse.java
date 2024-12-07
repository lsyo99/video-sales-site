package org.ItBridge.domain.LectureDetail.Controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LectureDetailResponse {
    private Long id;
    private String url;
    private Long lecture_id;
    private Integer sort_img;
}
