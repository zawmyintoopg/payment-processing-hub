package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public class QRPaymentRequestDTO {
    @NotBlank
    private String qrCode;
    @NotBlank
    private BigDecimal amount;
    @NotBlank
    private Long CurrencyId;
}
