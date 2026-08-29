package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementAIAnalysisResponse {

    private String summary;

    private Long totalTransactions;

    private String totalTransactionAmount;

    private String totalMdrAmount;

    private String totalSettlementAmount;

    private List<String> observations;

    private List<String> recommendations;
}