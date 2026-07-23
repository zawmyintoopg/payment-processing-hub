package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.AccountStatus;

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
    private Long currencyId;
    private String currencyCode;
    private AccountStatus status;
}
