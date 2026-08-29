package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionReportService {

        // Dashboard
        DashboardResponse getDashboardSummary(
                LocalDate fromDate,
                LocalDate toDate
        );

        // Transaction - UI
        Page<TransactionReportResponse> getTransactionReport(
                LocalDate fromDate,
                LocalDate toDate,
                Pageable pageable
        );

        // Transaction - Excel
        List<TransactionReportResponse> getTransactionReportForExport(
                LocalDate fromDate,
                LocalDate toDate
        );

        // Settlement
        List<SettlementReportResponse> getSettlementReport(
                LocalDate fromDate,
                LocalDate toDate
        );

        // Merchant
        List<MerchantSummaryResponse> getMerchantSummary(
                LocalDate fromDate,
                LocalDate toDate
        );

        // Terminal
        List<TerminalSummaryResponse> getTerminalSummary(
                LocalDate fromDate,
                LocalDate toDate
        );

        // Payment Channel
        List<PaymentChannelSummaryResponse> getPaymentChannelSummary(
                LocalDate fromDate,
                LocalDate toDate
        );

}