package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.*;
import com.mybank.paymenthub.service.ExcelExportService;
import com.mybank.paymenthub.service.SettlementAIAnalysisService;
import com.mybank.paymenthub.service.TransactionReportService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "10 - Report")
public class TransactionReportController {

    private final TransactionReportService transactionReportService;
    private final SettlementAIAnalysisService settlementAIAnalysisService;

    //dashboard summary
    @GetMapping("/dashboard")
    public DashboardResponse getDashboardSummary(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ){
        return transactionReportService.getDashboardSummary(
                fromDate,
                toDate
        );
    }
    @GetMapping("/transactions")
    public Page<TransactionReportResponse> getTransactionReport(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate,

            @PageableDefault(size = 20)
            Pageable pageable
    ) {

        return transactionReportService.getTransactionReport(
                fromDate,
                toDate,
                pageable
        );
    }
    // Settlement Report
    @GetMapping("/settlements")
    public List<SettlementReportResponse> getSettlementReport(
            @RequestParam LocalDate fromDate,
            @RequestParam LocalDate toDate
    ) {

        return transactionReportService.getSettlementReport(
                fromDate,toDate
        );
    }

    // Terminal Summary Report
    @GetMapping("/terminals/summary")
    public List<TerminalSummaryResponse> getTerminalSummary(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate

    ) {
        return transactionReportService.getTerminalSummary(
                fromDate,
                toDate
        );
    }
    @PostMapping("/settlements/ai-analysis")
    public SettlementAIAnalysisResponse analyzeSettlementReport(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate

    ) {

        List<SettlementReportResponse> settlements =
                transactionReportService.getSettlementReport(
                        fromDate,
                        toDate
                );

        return settlementAIAnalysisService.analyze(
                settlements
        );
    }

    @GetMapping("/payment-channels")
    public List<PaymentChannelSummaryResponse> getPaymentChannelSummary(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate
    ) {

        return transactionReportService.getPaymentChannelSummary(
                fromDate,
                toDate
        );
    }
}
