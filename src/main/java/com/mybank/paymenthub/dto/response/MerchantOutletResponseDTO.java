package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.MerchantStatus;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class MerchantOutletResponseDTO {
    private Long id;
    private String OutletNumber;
    private String OutletName;
    private String address;
    private String city;
    private String phone;
    private Long merchantId;
    private MerchantStatus status;
    private LocalDateTime createdAt;
}
