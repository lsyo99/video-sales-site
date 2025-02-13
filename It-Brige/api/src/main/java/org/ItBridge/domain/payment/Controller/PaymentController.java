package org.ItBridge.domain.payment.Controller;

import com.siot.IamportRestClient.exception.IamportResponseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ItBridge.Common.api.Api;
import org.ItBridge.Common.api.Result;
import org.ItBridge.Common.error.ErrorCode;
import org.ItBridge.Common.exception.ApiException;
import org.ItBridge.domain.payment.Business.PaymentBusiness;
import org.ItBridge.domain.payment.Controller.Model.PaymentRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PaymentController {
    private final PaymentBusiness paymentBusiness;

    @PostMapping("validate")
    public Api<String> validationPayment(
            @RequestBody PaymentRequest paymentRequest
            ) throws IamportResponseException, IOException {
        log.info("impuid 컨트롤러{}",paymentRequest.getImp_uid());
        boolean isPaymentValid = paymentBusiness.validateAndSavePayment(paymentRequest);
        if(isPaymentValid){
            return Api.ok("결제가 성공적입니다.");
        }
        else{
            return Api.ERROR("결제 실패");
        }
    }

}
