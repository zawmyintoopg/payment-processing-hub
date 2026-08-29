package com.mybank.paymenthub.dto.request;

import com.mybank.paymenthub.enums.TransactionType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class OperationTransactionRequest {

        @NotBlank(
                message = "Reference Number is required"
        )
        private String referenceNumber;

        @NotNull(
                message = "Payment Channel is required"
        )
        private Long paymentChannelId;

        @NotNull(
                message = "Terminal is required"
        )
        private Long terminalId;

        @NotNull(
                message = "Transaction Amount is required"
        )
        @DecimalMin(
                value = "0.01",
                message = "Transaction Amount must be greater than Zero"
        )
        private BigDecimal transactionAmount;

        @NotNull(
                message = "Currency is required"
        )
        private Long currencyId;

        @NotNull(
                message = "Transaction Type is required"
        )
        private TransactionType transactionType;

        @NotNull(
                message = "Payment Method is required"
        )
        private Long paymentMethodId;

        @NotBlank(
                message = "Payment Account is required"
        )
        private String maskedPaymentAccount;

        private Long merchantQRId;
}