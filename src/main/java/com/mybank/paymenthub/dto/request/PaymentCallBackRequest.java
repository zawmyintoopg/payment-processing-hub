package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.TransactionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PaymentCallBackRequest {

    @NotBlank(
            message = "Provider Reference Number is required"
    )
    private String providerReferenceNumber;

    @NotNull(
            message = "Transaction Status is required"
    )
    private TransactionStatus transactionStatus;

    private String externalReferenceNumber;

    private String authorizationCode;

    private String responseCode;

    private String responseMessage;

    private String retrievalReferenceNumber;
}