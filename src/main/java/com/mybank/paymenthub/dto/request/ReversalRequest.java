package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReversalRequest {

    @NotBlank(
            message = "Reason is required"
    )
    private String reason;
}