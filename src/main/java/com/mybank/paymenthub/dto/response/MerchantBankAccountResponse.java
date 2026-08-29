package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.AccountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantBankAccountResponse {
    private Long id;
    private Long merchantId;
    private String merchantName;
    private Long bankId;
    private String bankName;
    private String accountNumber;
    private String accountName;
    private Long accountTypeId;
    private String accountTypeName;
    private LocalDate openedDate;
    private Long currencyId;
    private String currencyCode;
    private AccountStatus status;
}
