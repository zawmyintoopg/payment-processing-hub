package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountTypeRequestDTO {

    @NotBlank(message = "Account Type is required!")
    @Size(max = 50, message = "Account Type must not exceed 50 characters")
    private String accountType;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    private String description;

    @NotNull(message = "Status is required!")
    private Status status;
}