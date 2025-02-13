package org.ItBridge.domain.MyPage.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.db.model.MyPageLectureEntity;
import org.ItBridge.domain.MyPage.Controller.model.MyPageLectureResponse;

import java.util.List;
import java.util.Optional;


@Converter
@RequiredArgsConstructor
public class MyPageConverter {
    public MyPageLectureResponse toResponse(MyPageLectureEntity myPageLectureEntity){
        return Optional.ofNullable(myPageLectureEntity).map(
                it->{
                        return MyPageLectureResponse.builder()
                            .payment_id(it.getPaymentId())
                            .lecture_id(it.getLectureId())
                            .thumbnail_url(it.getThumbnailUrl())
                            .title(it.getTitle())
                            .category(it.getCategory())
                            .first_price(it.getFirst_price())
                            .salse(it.getSalse())
                            .account(it.getAccount())
                            .payed_data(it.getPayed_date())
                            .pay_method(it.getPayed_method()).build();
                }
        ).orElseThrow(()-> new ApiException(ErrorCode.NULL_POINT,"값이 없음"));


    }
}
