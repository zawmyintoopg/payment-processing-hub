package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.SettlementResponse;

import java.math.BigDecimal;

public interface SettlementMerchantProjection {

    SettlementResponse createSettlement();
}
