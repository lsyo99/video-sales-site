package org.ItBridge.domain.MyPage.Business;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.domain.MyPage.Controller.model.MyPageLectureResponse;
import org.ItBridge.domain.MyPage.Converter.MyPageConverter;
import org.ItBridge.domain.MyPage.Service.MyPageService;

import java.util.List;
import java.util.stream.Collectors;

@Business
@RequiredArgsConstructor
public class MyPageBusiness {

    private final MyPageConverter myPageConverter;
    private final MyPageService myPageService;
    public List<MyPageLectureResponse> getMyLecture(Long userId) {
        var listMyPageLectureResponse = myPageService.getMyLecture(userId);

        return listMyPageLectureResponse.stream()
                .map(it->{
                    return myPageConverter.toResponse(it);
                }).collect(Collectors.toList());
    }
}
