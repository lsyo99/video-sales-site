package org.ItBridge.domain.MyPage.Controller;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.api.Api;
import org.ItBridge.domain.MyPage.Business.MyPageBusiness;
import org.ItBridge.domain.MyPage.Controller.model.MyPageLectureResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/open-api/mypage")
public class MypageController {
    private final MyPageBusiness myPageBusiness;

    @GetMapping("/mypage/{id}")
    public Api<List<MyPageLectureResponse>> getMyLecture(@PathVariable("id") Long user_id)
    {
        var response = myPageBusiness.getMyLecture(user_id);
        return Api.ok(response);
    }

}
