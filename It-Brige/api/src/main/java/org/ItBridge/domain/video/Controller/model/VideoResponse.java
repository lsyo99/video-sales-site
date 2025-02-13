package org.ItBridge.domain.video.Controller.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.ItBridge.Common.annotation.Business;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VideoResponse {
    private Long id;
    private String url;
    private Long lecture_id;
    private Integer sort_id;
    private String title;
    private String duration;
}
