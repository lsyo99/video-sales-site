package org.ItBridge.domain.payment.Converter;

import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.db.Lecture.LectureEntity;
import org.ItBridge.db.mypage.PaymentEntity;
import org.ItBridge.db.user.UserEntity;
import org.ItBridge.domain.payment.Controller.Model.PaymentRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Converter
@RequiredArgsConstructor
public class PaymentConverter {

    public PaymentEntity toEntity(PaymentRequest paymentRequest, UserEntity userEntity, LectureEntity lectureEntity) {
        return PaymentEntity.builder()
                .lecture(lectureEntity)
                .user(userEntity)
                .account(BigDecimal.valueOf(paymentRequest.getAmount()))
                .payedDate(LocalDateTime.now())
                .payedMethod(paymentRequest.getPay_method())
                .firstPrice(BigDecimal.valueOf(paymentRequest.getAmount()))
                .merchantUid(paymentRequest.getMerchant_uid())
                .impUid(paymentRequest.getImp_uid())
                .build();


    }
}
