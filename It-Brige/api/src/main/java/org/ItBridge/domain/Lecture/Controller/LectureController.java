package org.ItBridge.domain.Lecture.Controller;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.domain.Lecture.Business.LectureBusiness;
import org.ItBridge.domain.Lecture.Controller.model.LectureRequest;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.User.Business.UserBusiness;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/lecture")
public class LectureController {

    private final LectureBusiness lectureBusiness;
    @GetMapping("/best")
    public Api<List<LectureResponse>> getBestLecture(@RequestParam String category) {
        List<LectureResponse> response = lectureBusiness.getBestLectures(category);
        return Api.ok(response);
    }
}
