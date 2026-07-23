package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationTransactionRequestDTO {


    @NotNull(message = "Terminal ID can not be blank")
    private Long terminalInventoryId;


    @NotNull(message = "Merchant Outlet ID can not be blank")
    private Long merchantOutletId;


    @NotNull(message = "Transaction Date Time can not be blank")
    private LocalDateTime transactionDateTime;


    @NotNull(message = "Amount can not be blank")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    private BigDecimal amount;


    @NotNull(message = "Currency ID can not be blank")
    private Long currencyId;


    @NotNull(message = "Transaction Type can not be blank")
    private TransactionType transactionType;


    private String paymentMethod;


    /*
     * Example:
     * **** **** **** 1234
     */
    private String maskedCardNumber;


    private String authorizationCode;


    private String referenceNumber;

}