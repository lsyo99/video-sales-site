package org.ItBridge.domain.Lecture.Controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.domain.Lecture.Business.LectureBusiness;
import org.ItBridge.domain.Lecture.Controller.model.LectureResponse;
import org.ItBridge.domain.Lecture.Controller.model.LectureSaveReqeust;
import org.ItBridge.domain.Lecture.Service.LectureService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/saveLecture")
@RequiredArgsConstructor
public class LectureAdminController {
    private final LectureBusiness lectureBusiness;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Api<LectureResponse> uploadLecture(
            @RequestPart("courseData") LectureSaveReqeust lectureSaveReqeust,
            @RequestPart("thumbnail") MultipartFile thumbnail,
            @RequestPart("lecture_detail_images") List<MultipartFile> detailImages,
            @RequestPart("lecture_videos") List<MultipartFile> lectureVideos
    ) throws IOException {
        var response = lectureBusiness.saveLecture(lectureSaveReqeust,thumbnail,detailImages,detailImages);
        return Api.ok(response);
    }
}
