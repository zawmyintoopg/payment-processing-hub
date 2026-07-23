package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.QRStatus;
import com.mybank.paymenthub.enums.QRType;
import jakarta.validation.constraints.NotBlank;

public class MerchantQRRequestDTO {

    @NotBlank(message = "Merchant Outlet is required")
    private Long merchantOutletId;
    @NotBlank(message = "QR Type is required")
    private QRType qrType;
    @NotBlank(message = "QR Status is required")
    private QRStatus status;
    @NotBlank(message = "Provider Name is required")
    private String providerName;
}
