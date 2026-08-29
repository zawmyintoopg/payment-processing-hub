package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TerminalTypeRequest {

    @NotBlank(
            message = "Terminal Type Code is required"
    )
    @Size(
            max = 20,
            message = "Terminal Type Code must not exceed 20 characters"
    )
    private String typeCode;

    @NotBlank(
            message = "Terminal Type Name is required"
    )
    @Size(
            max = 100,
            message = "Terminal Type Name must not exceed 100 characters"
    )
    private String typeName;
}