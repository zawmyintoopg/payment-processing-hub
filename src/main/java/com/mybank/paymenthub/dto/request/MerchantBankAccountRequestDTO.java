package com.mybank.paymenthub.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantBankAccountRequestDTO {
    @NotNull(
            message = "Merchant is required !"
    )
    private Long merchantId;

    @NotNull(
            message = "Account's Bank is required!"
    )
    private Long bankId;

    @NotBlank(
            message = "Account Number is required !"
    )
    @Size(
            max = 17,
            min = 5,
            message = "Account Number exceed 17 characters"
    )
    private String accountNumber;

    @NotBlank(
            message = "Account Name is required!"
    )
    @Size(max = 150 ,
            message = "Account Name exceed 150 characters")
    private String accountName;

    @NotNull(
            message = "Account Type is required !"
    )
    private Long accountTypeId;

    @NotNull(
            message = "Account Opened Date is required !"
    )
    private LocalDate accountOpenedDate;

    @NotNull(message = "Currency is required !")
    private Long currencyId;

}
