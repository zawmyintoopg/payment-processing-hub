package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.SettlementAIAnalysisResponse;
import com.mybank.paymenthub.dto.response.SettlementReportResponse;

import java.util.List;

public interface SettlementAIAnalysisService {

    SettlementAIAnalysisResponse analyze(
            List<SettlementReportResponse> settlements
    );
}