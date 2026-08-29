package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.MerchantSummaryResponse;
import com.mybank.paymenthub.dto.response.SettlementReportResponse;
import com.mybank.paymenthub.dto.response.TerminalSummaryResponse;
import com.mybank.paymenthub.dto.response.TransactionReportResponse;

import java.time.LocalDate;
import java.util.List;

public interface ExcelExportService {

    byte[] exportTransactionReport(
            List<TransactionReportResponse> data,
            LocalDate fromDate,
            LocalDate toDate
    );

    byte[] exportMerchantSummary(
            List<MerchantSummaryResponse> data,
            LocalDate fromDate,
            LocalDate toDate
    );

    byte[] exportTerminalSummary(
            List<TerminalSummaryResponse> data,
            LocalDate fromDate,
            LocalDate toDate
    );

    byte[] exportSettlementReport(
            List<SettlementReportResponse> data,
            LocalDate fromDate,
            LocalDate toDate
    );
}