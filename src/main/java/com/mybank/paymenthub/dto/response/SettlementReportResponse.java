package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.SettlementStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettlementReportResponse {

    private String settlementNumber;

    private String merchantNumber;

    private String merchantName;

    private LocalDate settlementDate;

    private Long transactionCount;

    private BigDecimal totalTransactionAmount;

    private BigDecimal totalMdrAmount;

    private BigDecimal totalSettlementAmount;

    private SettlementStatus settlementStatus;
}