package com.mybank.paymenthub.dto.response;

import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.enums.TransactionType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationTransactionResponseDTO {


    private Long id;


    private String transactionNumber;

    private String referenceNumber;


    // Terminal Information
    private Long terminalInventoryId;
    private String terminalNumber;
    private String serialNumber;


    // Outlet Information
    private Long merchantOutletId;
    private String merchantOutletName;


    private LocalDateTime transactionDateTime;


    private BigDecimal amount;


    // Currency
    private Long currencyId;
    private String currencyCode;


    private TransactionType transactionType;


    private TransactionStatus transactionStatus;


    private String paymentMethod;


    private String maskedCardNumber;


    private String authorizationCode;

}