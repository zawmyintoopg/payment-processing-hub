package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.ContractStatus;
import com.mybank.paymenthub.enums.ContractType;
import com.mybank.paymenthub.enums.SettlementCycle;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
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
public class MerchantContractCreateRequest {

    @NotNull(
            message = "Merchant is required"
    )
    private Long merchantId;


    @NotNull(
            message = "Contract Type is required"
    )
    private ContractType contractType;


    @NotNull(
            message = "Contract Date is required"
    )
    private LocalDate contractDate;


    @NotNull(
            message = "Contract StartDate is required"
    )
    private LocalDate contractStartDate;


    @NotNull(
            message = "Contract EndDate is required"
    )
    private LocalDate contractEndDate;


    @NotNull(
            message = "Commission Rate is required"
    )
    private BigDecimal commissionRate;


    @NotNull(
            message = "Settlement Cycle is required "
    )
    private SettlementCycle settlementCycle;

}


