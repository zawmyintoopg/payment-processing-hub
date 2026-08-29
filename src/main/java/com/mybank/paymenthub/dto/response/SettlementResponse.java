package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.SettlementStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SettlementResponse {

    private String settlementNumber;

    private Long merchantId;

    private String merchantName;

    private Long merchantBankAccountId;

    private String merchantBankAccountName;

    private LocalDate settlementDate;

    private BigDecimal totalAmount;

    private Integer transactionCount;

    private SettlementStatus status;
}
