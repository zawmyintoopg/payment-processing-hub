package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.entity.Settlement;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SettlementDetailsResponse {
    private Settlement settlementId;
    private String settlementNumber;
    private LocalDate settlementDate;
    private Long transactionId;
    private String transactionNumber;
    private BigDecimal settlementAmount;
}
