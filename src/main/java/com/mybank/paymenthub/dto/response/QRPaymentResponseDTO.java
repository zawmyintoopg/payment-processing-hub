package com.mybank.paymenthub.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class QRPaymentResponseDTO {
    private String transactionNumber;
    private String qrCode;
    private String merchantName;
    private BigDecimal amount;
    private String status;

}
