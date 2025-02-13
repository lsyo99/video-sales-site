package org.ItBridge.domain.payment.Controller.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequest {
    @NotBlank
    private String imp_uid;
    @NotBlank
    private String merchant_uid;
    @NotNull
    private int amount;
    @NotBlank
    private String buyerEmail;

    private String buyerName;
    @NotNull
    private Long user_id;
    @NotNull
    private Long lecture_id;
    @NotBlank
    private String pay_method;

}
