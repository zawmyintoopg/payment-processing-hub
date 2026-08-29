package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.PaymentChannelSummaryResponse;

import java.time.LocalDate;
import java.util.List;

public interface PaymentChannelReportService {

    List<PaymentChannelSummaryResponse> getPaymentChannelSummary(
            LocalDate fromDate,
            LocalDate toDate
    );
}