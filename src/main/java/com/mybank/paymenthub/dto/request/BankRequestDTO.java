package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BankRequestDTO {

    @NotBlank(message = "Bank Code is required!")
    @Size(max = 20, message = "Bank Code must not exceed 20 characters")
    private String bankCode;

    @NotBlank(message = "Bank Name is required!")
    @Size(max = 100, message = "Bank Name must not exceed 100 characters")
    private String bankName;

    @Size(max = 50, message = "Short Name must not exceed 50 characters")
    private String shortName;

    @NotNull(message = "Status is required!")
    private Status status;
}