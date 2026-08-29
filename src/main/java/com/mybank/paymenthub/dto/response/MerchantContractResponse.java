package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.ContractStatus;
import com.mybank.paymenthub.enums.ContractType;
import com.mybank.paymenthub.enums.SettlementCycle;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantContractResponse {
    private Long id;
    private String contractNumber;
    private Long merchantId;
    private String merchantName;
    private ContractType contractType;
    private LocalDate contractDate;
    private LocalDate contractStartDate;
    private LocalDate contractEndDate;
    private BigDecimal commissionRate;
    private SettlementCycle settlementCycle;
    private ContractStatus status;
}
