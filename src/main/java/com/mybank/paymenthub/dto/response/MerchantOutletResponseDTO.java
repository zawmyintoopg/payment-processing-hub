package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.OutletStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
public class MerchantOutletResponseDTO {
    private Long id;
    private String outletNumber;
    private String outletName;

    private String address;
    private String city;
    private String phone;

    private Long merchantId;
    private String merchantName;

    private OutletStatus status;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
