package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.AccountStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class MerchantBankAccountRequestDTO {
    @NotNull(message = "Merchant ID can not be blank !")
    private Long merchantId;
    @NotNull(message = "Bank ID can not be blank !")
    private Long bankId;
    @NotBlank(message = "Account Number can not be blank !")
    @Size(max = 17 ,min = 5, message = "Bank account can not be more than 17 Characters")
    private String accountNumber;
    @NotBlank(message = "Account Name can not be blank !")
    @Size(max = 150 , message = "Bank account can not be more than 150 Characters")
    private String accountName;
    @NotNull(message = "Account Type can not be blank !")
    private Long accountTypeId;
    @NotNull(message = "Currency ID can not be blank !")
    private Long currencyId;
    @NotNull(message = "Status can not be blank !")
    private AccountStatus status;
}
