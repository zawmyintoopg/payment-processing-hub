package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.response.PaymentMethodSummaryResponse;
import com.mybank.paymenthub.repository.OperationTransactionRepository;
import com.mybank.paymenthub.repository.projection.PaymentMethodSummaryProjection;
import com.mybank.paymenthub.service.PaymentMethodReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentMethodReportServiceImpl
        implements PaymentMethodReportService {

    private final OperationTransactionRepository operationTransactionRepository;

    @Override
    public List<PaymentMethodSummaryResponse> getPaymentMethodSummary(
            LocalDate fromDate,
            LocalDate toDate
    ) {

        List<PaymentMethodSummaryProjection> results =
                operationTransactionRepository.findPaymentMethodSummary(
                        fromDate.atStartOfDay(),
                        toDate.plusDays(1).atStartOfDay()
                );

        return results.stream()
                .map(result -> new PaymentMethodSummaryResponse(
                        result.getPaymentCode(),
                        result.getPaymentMethod(),
                        result.getTotalTransactions(),
                        result.getSuccessfulTransactions(),
                        result.getFailedTransactions(),
                        result.getReversedTransactions(),
                        result.getTotalTransactionAmount(),
                        result.getTotalMdrAmount(),
                        result.getTotalSettlementAmount()
                ))
                .toList();
    }
}