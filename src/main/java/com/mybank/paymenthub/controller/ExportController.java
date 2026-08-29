package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.*;
import com.mybank.paymenthub.service.ExcelExportService;
import com.mybank.paymenthub.service.PdfExportService;
import com.mybank.paymenthub.service.TransactionReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/excel_export")
@RequiredArgsConstructor
public class ExportController {

    private final TransactionReportService transactionReportService;

    private final ExcelExportService excelExportService;



    // =========================================================
    // Merchant Summary Excel Export
    // =========================================================

    @GetMapping("/merchants/summary/export")
    public ResponseEntity<byte[]> exportMerchantSummary(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate

    ) {

        List<MerchantSummaryResponse> merchants =
                transactionReportService.getMerchantSummary(
                        fromDate,
                        toDate
                );

        byte[] file =
                excelExportService.exportMerchantSummary(
                        merchants,
                        fromDate,
                        toDate
                );

        String fileName =
                "Merchant_Summary_Report_" +
                        fromDate +
                        "_to_" +
                        toDate +
                        ".xlsx";

        return buildExcelResponse(
                file,
                fileName
        );
    }


    // =========================================================
    // Terminal Summary Excel Export
    // =========================================================

    @GetMapping("/terminals/summary/export")
    public ResponseEntity<byte[]> exportTerminalSummary(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate

    ) {

        List<TerminalSummaryResponse> terminals =
                transactionReportService.getTerminalSummary(
                        fromDate,
                        toDate
                );

        byte[] file =
                excelExportService.exportTerminalSummary(
                        terminals,
                        fromDate,
                        toDate
                );

        String fileName =
                "Terminal_Summary_Report_" +
                        fromDate +
                        "_to_" +
                        toDate +
                        ".xlsx";

        return buildExcelResponse(
                file,
                fileName
        );
    }


    // =========================================================
    // Transaction Report Excel Export
    // =========================================================

    @GetMapping("/transactions/export")
    public ResponseEntity<byte[]> exportTransactionReport(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate

    ) {

        List<TransactionReportResponse> transactions =
                transactionReportService
                        .getTransactionReportForExport(
                                fromDate,
                                toDate
                        );

        byte[] file =
                excelExportService.exportTransactionReport(
                        transactions,
                        fromDate,
                        toDate
                );

        String fileName =
                "Transaction_Report_" +
                        fromDate +
                        "_to_" +
                        toDate +
                        ".xlsx";

        return buildExcelResponse(
                file,
                fileName
        );
    }


    // =========================================================
    // Settlement Report Excel Export
    // =========================================================

    @GetMapping("/settlements/export")
    public ResponseEntity<byte[]> exportSettlementReport(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate

    ) {

        List<SettlementReportResponse> settlements =
                transactionReportService.getSettlementReport(
                        fromDate,
                        toDate
                );

        byte[] file =
                excelExportService.exportSettlementReport(
                        settlements,
                        fromDate,
                        toDate
                );

        String fileName =
                "Settlement_Report_" +
                        fromDate +
                        "_to_" +
                        toDate +
                        ".xlsx";

        return buildExcelResponse(
                file,
                fileName
        );
    }


    // =========================================================
    // Common Excel Response
    // =========================================================

    private ResponseEntity<byte[]> buildExcelResponse(
            byte[] file,
            String fileName
    ) {

        return ResponseEntity.ok()

                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                fileName +
                                "\""
                )

                .contentType(
                        MediaType.parseMediaType(
                                "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                        )
                )

                .body(file);
    }


}