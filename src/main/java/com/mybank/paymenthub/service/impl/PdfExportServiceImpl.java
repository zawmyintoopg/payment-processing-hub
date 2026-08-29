package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.response.DashboardResponse;
import com.mybank.paymenthub.pdf.PaymentHubDailyReportPdfExporter;
import com.mybank.paymenthub.service.PdfExportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PdfExportServiceImpl
        implements PdfExportService {

    private final PaymentHubDailyReportPdfExporter
            pdfExporter;

    @Override
    public byte[] exportDailyReport(
            DashboardResponse data,
            LocalDate reportDate
    ) {

        return pdfExporter.export(
                data,
                reportDate
        );
    }
}