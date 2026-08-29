package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TerminalInventoryRequestDTO{
    @NotBlank(
            message = "Terminal Serial Number is required"
    )
    @Size(max = 50, message = "Terminal Serial Number must not exceed 50 characters")
    private String terminalSerialNumber;


    @NotBlank(
            message = "Manufacturer is required"
    )
    @Size(max = 50, message = "Manufacturer must not exceed 50 characters")
    private String manufacturer;


    @NotBlank(
            message = "Model is required"
    )
    @Size(max = 50, message = "Model must not exceed 50 characters")
    private String model;


    @NotNull(
            message = "Purchase Date is required"
    )
    private LocalDate purchasedDate;


    @NotNull(
            message = "Terminal Type is required"
    )
    private Long terminalTypeId;

}

