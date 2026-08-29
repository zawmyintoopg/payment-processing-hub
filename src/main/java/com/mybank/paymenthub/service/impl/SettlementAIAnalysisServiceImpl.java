package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.response.SettlementAIAnalysisResponse;
import com.mybank.paymenthub.dto.response.SettlementReportResponse;
import com.mybank.paymenthub.service.SettlementAIAnalysisService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
public class SettlementAIAnalysisServiceImpl
        implements SettlementAIAnalysisService {

    @Override
    public SettlementAIAnalysisResponse analyze(
            List<SettlementReportResponse> settlements
    ) {

        long totalTransactions = settlements.stream()
                .mapToLong(s ->
                        s.getTransactionCount() != null
                                ? s.getTransactionCount()
                                : 0L
                )
                .sum();

        BigDecimal totalTransactionAmount =
                settlements.stream()
                        .map(SettlementReportResponse::getTotalTransactionAmount)
                        .filter(value -> value != null)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalMdrAmount =
                settlements.stream()
                        .map(SettlementReportResponse::getTotalMdrAmount)
                        .filter(value -> value != null)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalSettlementAmount =
                settlements.stream()
                        .map(SettlementReportResponse::getTotalSettlementAmount)
                        .filter(value -> value != null)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);

        long createdCount = settlements.stream()
                .filter(s ->
                        s.getSettlementStatus() != null &&
                                s.getSettlementStatus().name().equals("CREATED")
                )
                .count();

        long completedCount = settlements.stream()
                .filter(s ->
                        s.getSettlementStatus() != null &&
                                s.getSettlementStatus().name().equals("COMPLETED")
                )
                .count();

        List<String> observations = new ArrayList<>();

        if (createdCount > 0) {
            observations.add(
                    createdCount +
                            " settlement(s) are still in CREATED status."
            );
        }

        if (completedCount > 0) {
            observations.add(
                    completedCount +
                            " settlement(s) have been COMPLETED."
            );
        }

        if (totalTransactionAmount.compareTo(BigDecimal.ZERO) > 0) {

            BigDecimal calculatedSettlement =
                    totalTransactionAmount.subtract(totalMdrAmount);

            if (calculatedSettlement.compareTo(
                    totalSettlementAmount) != 0
            ) {
                observations.add(
                        "Transaction amount minus MDR does not match " +
                                "the reported settlement amount."
                );
            } else {
                observations.add(
                        "Settlement amount matches transaction amount " +
                                "minus MDR."
                );
            }
        }

        List<String> recommendations = new ArrayList<>();

        if (createdCount > 0) {
            recommendations.add(
                    "Monitor CREATED settlements and verify that " +
                            "the settlement process completes successfully."
            );
        }

        if (settlements.isEmpty()) {
            recommendations.add(
                    "No settlement data was found for the selected date range."
            );
        }

        String summary =
                "The report contains " +
                        totalTransactions +
                        " transactions with a total transaction amount of " +
                        formatAmount(totalTransactionAmount) +
                        ". Total MDR is " +
                        formatAmount(totalMdrAmount) +
                        ", resulting in a settlement amount of " +
                        formatAmount(totalSettlementAmount) +
                        ".";

        return new SettlementAIAnalysisResponse(
                summary,
                totalTransactions,
                formatAmount(totalTransactionAmount),
                formatAmount(totalMdrAmount),
                formatAmount(totalSettlementAmount),
                observations,
                recommendations
        );
    }

    private String formatAmount(BigDecimal amount) {

        return amount
                .setScale(2, RoundingMode.HALF_UP)
                .toPlainString();
    }
}