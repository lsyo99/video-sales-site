package org.ItBridge.domain.Lecture.Controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LectureRequest {
    private String category;

    private Boolean updatenew;
}
