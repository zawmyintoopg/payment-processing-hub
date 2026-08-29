package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MerchantSegmentRequest {
    @NotBlank(
            message="Merchant Segment Code is required"
    )
    private String merchantSegmentCode;

    @NotBlank(
            message="Merchant Segment Name is required"
    )
    private String  merchantSegmentName;
}
