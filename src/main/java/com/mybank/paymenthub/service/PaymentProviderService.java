package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.ProviderReversalRequest;
import com.mybank.paymenthub.dto.response.ProviderReversalResponse;

public interface PaymentProviderService {

    ProviderReversalResponse reverse(
            ProviderReversalRequest request
    );
}
