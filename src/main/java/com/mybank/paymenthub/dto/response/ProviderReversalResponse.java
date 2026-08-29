package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderReversalResponse {

    private String providerReferenceNumber;

    private TransactionStatus transactionStatus;

    private String responseCode;

    private String responseMessage;
}