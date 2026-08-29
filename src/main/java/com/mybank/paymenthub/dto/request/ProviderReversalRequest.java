package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProviderReversalRequest {

    @NotBlank(
            message = "Provider Reference Number is required"
    )
    private String providerReferenceNumber;

    @NotBlank(
            message = "Reversal reason is required"
    )
    private String reason;
}