package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.request.OperationTransactionRequest;
import com.mybank.paymenthub.dto.request.PaymentCallBackRequest;
import com.mybank.paymenthub.dto.request.ReversalRequest;
import com.mybank.paymenthub.dto.response.OperationTransactionResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OperationTransactionService {

    // ======================================================
    // PAYMENT
    // ======================================================

    OperationTransactionResponse create(
            OperationTransactionRequest request
    );

    OperationTransactionResponse processPaymentCallBack(
            String transactionNumber,
            PaymentCallBackRequest request
    );


    // ======================================================
    // QUERY
    // ======================================================

    Page<OperationTransactionResponse> getAll(
            String search,
            Pageable pageable
    );

    OperationTransactionResponse getById(
            Long transactionId
    );

    OperationTransactionResponse getByTransactionNumber(
            String transactionNumber
    );


    // ======================================================
    // REVERSAL
    // ======================================================

    OperationTransactionResponse requestReversal(
            String transactionNumber,
            ReversalRequest request
    );

    OperationTransactionResponse processReversalCallBack(
            String transactionNumber,
            PaymentCallBackRequest request
    );
}