package com.mybank.paymenthub.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MerchantOutletMerchantHistoryResponseDTO {

    private Long id;

    private Long merchantOutletId;
    private String outletNumber;
    private String outletName;

    private Long merchantId;
    private String merchantName;

    private LocalDateTime effectiveFrom;
    private LocalDateTime effectiveTo;

    private String changeReason;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}