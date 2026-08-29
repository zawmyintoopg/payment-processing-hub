package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.DashboardResponse;
import com.mybank.paymenthub.service.PdfExportService;
import com.mybank.paymenthub.service.TransactionReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
@RestController
@RequestMapping("/api/v1/pdf_export")
@RequiredArgsConstructor
public class pdfExportController {
    private final PdfExportService pdfExportService;
    private final TransactionReportService transactionReportService;

    @GetMapping("/dashboard")
    public ResponseEntity<byte[]> exportDashboardPdf(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ) {

        DashboardResponse data =
                transactionReportService.getDashboardSummary(
                        fromDate,
                        toDate
                );

        byte[] file =
                pdfExportService.exportDailyReport(
                        data,
                        fromDate
                );

        String fileName =
                "Payment_Hub_Daily_Report_" +
                        fromDate +
                        ".pdf";

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" +
                                fileName +
                                "\""
                )
                .contentType(
                        MediaType.APPLICATION_PDF
                )
                .body(file);
    }
}
