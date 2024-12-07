package org.ItBridge.domain.LectureDetail.Controller;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.db.LectureDetail.LectureDetailEntity;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.LectureDetail.Business.LectureDetailBusiness;
import org.ItBridge.domain.LectureDetail.Controller.model.LectureDetailResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/open-api/lecture")
public class LectureDetailController {

    private final LectureDetailBusiness lectureDetailBusiness;
//
@GetMapping("/{lectureId}/images")
public Api<List<LectureDetailResponse>> getLectureImages(@PathVariable Long lectureId) {
    var response = lectureDetailBusiness.getLectureImages(lectureId);

        return Api.ok(response);
    }
}
