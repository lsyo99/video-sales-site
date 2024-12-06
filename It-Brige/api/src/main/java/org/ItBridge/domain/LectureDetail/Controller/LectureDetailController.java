package org.ItBridge.domain.LectureDetail.Controller;

import lombok.RequiredArgsConstructor;
import org.ItBridge.domain.LectureDetail.Business.LectureDetailBusiness;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor

@RestController
@RequestMapping("/lecture/Detail")
public class LectureDetailController {

    private final LectureDetailBusiness lectureDetailBusiness;
//
//    @GetMapping('/lecture/{id}')
//    public Api<lectureDetailResponse>
}
