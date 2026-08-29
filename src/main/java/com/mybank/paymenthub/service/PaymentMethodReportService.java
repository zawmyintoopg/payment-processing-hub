package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.PaymentMethodSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface PaymentMethodReportService {

    List<PaymentMethodSummaryResponse> getPaymentMethodSummary(
            LocalDate fromDate,
            LocalDate toDate
    );
}