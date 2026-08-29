package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.DashboardResponse;

import java.time.LocalDate;

public interface PdfExportService {

    byte[] exportDailyReport(
            DashboardResponse data,
            LocalDate reportDate
    );
}