package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.MerchantSummaryResponse;
import com.mybank.paymenthub.service.TransactionReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class getMerchantSummaryController {

    private final TransactionReportService transactionReportService;
    @GetMapping("/merchants/summary")
    public List<MerchantSummaryResponse> getMerchantSummary(
            @RequestParam("fromDate") LocalDate fromDate,
            @RequestParam("toDate") LocalDate toDate
    ) {

        System.out.println("========== MERCHANT SUMMARY ==========");
        System.out.println("fromDate = " + fromDate);
        System.out.println("toDate   = " + toDate);

        return transactionReportService.getMerchantSummary(
                fromDate,
                toDate
        );
    }
}
