package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MerchantSegmentResponse {

    private String merchantSegmentCode;
    private String merchantSegmentName;
    private Status merchantSegmentStatus;
}
