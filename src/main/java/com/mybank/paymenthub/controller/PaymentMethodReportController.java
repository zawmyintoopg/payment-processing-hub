package com.mybank.paymenthub.controller;

import com.mybank.paymenthub.dto.response.PaymentMethodSummaryResponse;
import com.mybank.paymenthub.service.PaymentMethodReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class PaymentMethodReportController {

    private final PaymentMethodReportService paymentMethodReportService;

    @GetMapping("/payment-methods")
    public List<PaymentMethodSummaryResponse> getPaymentMethodSummary(

            @RequestParam LocalDate fromDate,

            @RequestParam LocalDate toDate
    ) {

        return paymentMethodReportService.getPaymentMethodSummary(
                fromDate,
                toDate
        );
    }
}