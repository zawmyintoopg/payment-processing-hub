package com.mybank.paymenthub.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementProcessResponse {
    private int settlementCount;
    private int transactionCount;
    private BigDecimal totalAmount;
    private List<SettlementResponse> settlements;
}
