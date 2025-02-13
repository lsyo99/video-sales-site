package org.ItBridge.domain.payment.Business;

import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Business;
import org.ItBridge.db.mypage.PaymentEntity;
import org.ItBridge.domain.Lecture.Service.LectureService;
import org.ItBridge.domain.User.Service.UserService;
import org.ItBridge.domain.payment.Controller.Model.PaymentRequest;
import org.ItBridge.domain.payment.Converter.PaymentConverter;
import org.ItBridge.domain.payment.Service.PaymentService;

import java.io.IOException;

@Business
@RequiredArgsConstructor
public class PaymentBusiness {
    private final PaymentService paymentService; // 서비스 계층 호출
    private final PaymentConverter paymentConverter; // 요청 → 엔티티 변환
    private final UserService userService;
    private final LectureService lectureService;

    public boolean validateAndSavePayment(PaymentRequest paymentRequest) throws IamportResponseException, IOException {

            var userEntity = userService.getUserId(paymentRequest.getUser_id());
            var lectureEntity = lectureService.getLecture(paymentRequest.getLecture_id());

            // 1. 결제 검증 및 저장
            var paymentEntity = paymentConverter.toEntity(paymentRequest, userEntity,lectureEntity);
            return paymentService.validateAndSavePayment(paymentEntity);

    }
}
