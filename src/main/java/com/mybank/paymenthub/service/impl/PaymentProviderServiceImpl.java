package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.ProviderReversalRequest;
import com.mybank.paymenthub.dto.response.ProviderReversalResponse;
import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.service.NumberSequenceService;
import com.mybank.paymenthub.service.PaymentProviderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentProviderServiceImpl implements PaymentProviderService {

    private final NumberSequenceService numberSequenceService;

    @Override
    public ProviderReversalResponse reverse(
            ProviderReversalRequest request
    ) {

        System.out.println(
                "Sending reversal request to dummy provider"
        );

        System.out.println(
                "Provider Reference: "
                        + request.getProviderReferenceNumber()
        );

        System.out.println(
                "Reason: "
                        + request.getReason()
        );

        return new ProviderReversalResponse(
                "PROVIDER-REV-REV001",
                TransactionStatus.REVERSAL_PENDING,
                "00",
                "Reversal request accepted"
        );
    }
}
