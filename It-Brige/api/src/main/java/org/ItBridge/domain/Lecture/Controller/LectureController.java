package org.ItBridge.domain.Lecture.Controller;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.domain.Lecture.Business.LectureBusiness;
import org.ItBridge.domain.Lecture.Controller.model.LectureRequest;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.User.Business.UserBusiness;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/lecture")
public class LectureController {

    private final LectureBusiness lectureBusiness;
    @GetMapping("/forpay/{lectureId}")
    Api<LectureResponse> getLecture(
            @PathVariable Long lectureId
    ){
        var response = lectureBusiness.getLecture(lectureId);
        return Api.ok(response);
    }

    @GetMapping("/{type}")
    public Api<List<LectureResponse>> getLectures(
            @PathVariable String type,
            @RequestParam String category) {
        List<LectureResponse> response;

        // 요청 타입에 따라 비즈니스 로직 분기
        switch (type.toLowerCase()) {
            case "best":
                response = lectureBusiness.getBestLectures(category);
                break;
            case "new":
                response = lectureBusiness.getNewLectures(category);
                break;
            default:
                throw new IllegalArgumentException("Invalid lecture type: " + type);
        }

        return Api.ok(response);
    }
    @GetMapping("/headerToCourse/{type}")
    public Api<List<LectureResponse>> getLecturesHeaderToCourse(
            @PathVariable String type
            ) {

        // 요청 타입에 따라 비즈니스 로직 분기
        var lowerType = type.toLowerCase();
        var response = lectureBusiness.getHeaderLecture(lowerType);

        return Api.ok(response);
    }
    @GetMapping("/search")
    public Api<List<LectureResponse>> searchCourses(@RequestParam String search) {
        List<LectureResponse> courses = lectureBusiness.searchCourses(search);
        return Api.ok(courses);
    }


}
