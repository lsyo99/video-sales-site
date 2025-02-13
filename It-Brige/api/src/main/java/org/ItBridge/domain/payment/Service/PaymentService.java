package org.ItBridge.domain.payment.Service;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.ItBridge.Common.annotation.Converter;
import org.ItBridge.db.mypage.PaymentEntity;
import org.ItBridge.db.mypage.PaymentRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final IamportClient iamportClient = new IamportClient("7431125687400733", "kD1zLX0lsDloUC6Y7rk9Vdv8EOKV4Ym8Qm9l3mbCGukb8df7A7Qzjc9c0gEs66zPlyFo8T2k8uswOCYw"); // 포트원 클라이언트
    private final PaymentRepository paymentRepository; // 결제 정보 저장소

    @PostConstruct
    public void init() {
    }
    @Transactional
    public boolean validateAndSavePayment(PaymentEntity paymentEntity) throws IamportResponseException, IOException {
        // 1. 포트원 API를 사용하여 결제 정보 가져오기
        IamportResponse<Payment> paymentResponse = iamportClient.paymentByImpUid(paymentEntity.getImpUid());

        if (paymentResponse == null || paymentResponse.getResponse() == null) {
            throw new RuntimeException("포트원 결제 정보를 가져올 수 없습니다.");
        }

        Payment payment = paymentResponse.getResponse();

        // 2. 결제 정보 검증
        if (!payment.getMerchantUid().equals(paymentEntity.getMerchantUid())) {
            throw new RuntimeException("주문번호가 일치하지 않습니다.");
        }

        if (payment.getAmount().compareTo(paymentEntity.getAccount()) != 0) {
            throw new RuntimeException("결제 금액이 일치하지 않습니다.");
        }

        if (!"paid".equals(payment.getStatus())) {
            throw new RuntimeException("결제가 완료되지 않았습니다.");
        }

        // 3. 결제 정보 저장
        paymentRepository.save(paymentEntity);
        return true;
}}
