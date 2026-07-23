package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.QRStatus;
import com.mybank.paymenthub.enums.QRType;
import jakarta.validation.constraints.NotBlank;

public class MerchantQRResponseDTO {
    private Long id;
    private String qrCode;
    private Long merchantOutletId;
    private String merchantOutletName;
    private QRType qrType;
    private QRStatus status;
    private String providerName;
}
