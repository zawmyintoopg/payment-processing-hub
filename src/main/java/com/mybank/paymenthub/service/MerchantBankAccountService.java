package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.MerchantBankAccountRequestDTO;
import com.mybank.paymenthub.dto.response.MerchantBankAccountResponse;

public interface MerchantBankAccountService {

    MerchantBankAccountResponse create(
            MerchantBankAccountRequestDTO request
    );
}