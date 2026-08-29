package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.response.MerchantSummaryResponse;
import com.mybank.paymenthub.dto.response.SettlementReportResponse;
import com.mybank.paymenthub.dto.response.TerminalSummaryResponse;
import com.mybank.paymenthub.dto.response.TransactionReportResponse;
import com.mybank.paymenthub.excel.TerminalSummaryExcelExporter;
import com.mybank.paymenthub.excel.TransactionReportExcelExporter;
import com.mybank.paymenthub.service.ExcelExportService;
import com.mybank.paymenthub.excel.MerchantSummaryExcelExporter;
import com.mybank.paymenthub.excel.SettlementReportExcelExporter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExcelExportServiceImpl
        implements ExcelExportService {

    private final SettlementReportExcelExporter
            settlementReportExcelExporter;

    private final MerchantSummaryExcelExporter
            merchantSummaryExcelExporter;

    private final TerminalSummaryExcelExporter
            terminalSummaryExcelExporter;

    private final TransactionReportExcelExporter
            transactionReportExcelExporter;


    @Override
    public byte[] exportSettlementReport(
            List<SettlementReportResponse> settlements,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        return settlementReportExcelExporter.export(
                settlements,
                fromDate,
                toDate
        );
    }


    @Override
    public byte[] exportMerchantSummary(
            List<MerchantSummaryResponse> data,
            LocalDate fromDate,
            LocalDate toDate
    ) {

        return merchantSummaryExcelExporter.export(
                data,
                fromDate,
                toDate
        );
    }

    @Override
    public byte[] exportTerminalSummary(
            List<TerminalSummaryResponse> data,
            LocalDate fromDate,
            LocalDate toDate
    ) {
        return terminalSummaryExcelExporter.export(
                data,
                fromDate,
                toDate
        );
    }
    @Override
    public byte[] exportTransactionReport(
            List<TransactionReportResponse> data,
            LocalDate fromDate,
            LocalDate toDate
    ){
        return transactionReportExcelExporter.export(
                data,
                fromDate,
                toDate
        );
    }



}