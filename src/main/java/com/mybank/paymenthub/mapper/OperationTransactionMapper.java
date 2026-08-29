package com.mybank.paymenthub.mapper;

import com.mybank.paymenthub.dto.request.OperationTransactionRequest;
import com.mybank.paymenthub.dto.response.OperationTransactionResponse;
import com.mybank.paymenthub.entity.*;
import com.mybank.paymenthub.enums.TransactionStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OperationTransactionMapper {

    // =========================================================
    // Request -> Entity
    // =========================================================

    public OperationTransaction toEntity(
            OperationTransactionRequest request,
            TerminalInventory terminal,
            MerchantOutlet outlet,
            Merchant merchant,
            Currency currency,
            PaymentMethod paymentMethod,
            PaymentChannel paymentChannel
    ) {

        OperationTransaction entity =
                new OperationTransaction();

        // -----------------------------------------------------
        // Reference
        // -----------------------------------------------------

        entity.setReferenceNumber(
                request.getReferenceNumber()
        );

        // -----------------------------------------------------
        // Relationships
        // -----------------------------------------------------

        entity.setTerminal(
                terminal
        );

        entity.setMerchant(
                merchant
        );

        entity.setMerchantOutlet(
                outlet
        );

        entity.setCurrency(
                currency
        );

        entity.setPaymentMethod(
                paymentMethod
        );

        entity.setPaymentChannel(
                paymentChannel
        );

        // -----------------------------------------------------
        // Transaction Information
        // -----------------------------------------------------

        entity.setTransactionAmount(
                request.getTransactionAmount()
        );

        entity.setTransactionType(
                request.getTransactionType()
        );

        entity.setMaskedPaymentAccount(
                request.getMaskedPaymentAccount()
        );

        entity.setTransactionTimestamp(
                LocalDateTime.now()
        );

        // -----------------------------------------------------
        // Initial Transaction Status
        // -----------------------------------------------------

        entity.setTransactionStatus(
                TransactionStatus.INITIATED
        );

        // -----------------------------------------------------
        // Reversal
        // -----------------------------------------------------

        entity.setIsReversed(false);

        return entity;
    }


    // =========================================================
    // Entity -> Response
    // =========================================================

    public OperationTransactionResponse toResponse(
            OperationTransaction entity
    ) {

        OperationTransactionResponse response =
                new OperationTransactionResponse();

        // -----------------------------------------------------
        // Transaction Number
        // -----------------------------------------------------

        response.setTransactionNumber(
                entity.getTransactionNumber()
        );

        // -----------------------------------------------------
        // Payment Channel
        // -----------------------------------------------------

        if (entity.getPaymentChannel() != null) {

            response.setPaymentChannelId(
                    entity.getPaymentChannel().getId()
            );

            response.setPaymentChannel(
                    entity.getPaymentChannel().getChannelName()
            );
        }

        // -----------------------------------------------------
        // Reference
        // -----------------------------------------------------

        response.setReferenceNumber(
                entity.getReferenceNumber()
        );

        response.setProviderReferenceNumber(
                entity.getProviderReferenceNumber()
        );

        // -----------------------------------------------------
        // Terminal
        // -----------------------------------------------------

        if (entity.getTerminal() != null) {

            response.setTerminalId(
                    entity.getTerminal().getId()
            );

            response.setTerminalNumber(
                    entity.getTerminal().getTerminalNumber()
            );

            if (entity.getTerminal().getTerminalType() != null) {

                response.setTerminalType(
                        entity.getTerminal()
                                .getTerminalType()
                                .getTypeName()
                );
            }
        }

        // -----------------------------------------------------
        // Merchant
        // -----------------------------------------------------

        if (entity.getMerchant() != null) {

            response.setMerchantId(
                    entity.getMerchant().getId()
            );

            response.setMerchantName(
                    entity.getMerchant().getMerchantName()
            );
        }

        // -----------------------------------------------------
        // Merchant Outlet
        // -----------------------------------------------------

        if (entity.getMerchantOutlet() != null) {

            response.setMerchantOutletId(
                    entity.getMerchantOutlet().getId()
            );

            response.setMerchantOutletName(
                    entity.getMerchantOutlet().getOutletName()
            );
        }

        // -----------------------------------------------------
        // Transaction Timestamp
        // -----------------------------------------------------

        response.setTransactionTimestamp(
                entity.getTransactionTimestamp()
        );

        // -----------------------------------------------------
        // Transaction Amount
        // -----------------------------------------------------

        response.setTransactionAmount(
                entity.getTransactionAmount()
        );

        // -----------------------------------------------------
        // Currency
        // -----------------------------------------------------

        if (entity.getCurrency() != null) {

            response.setCurrencyId(
                    entity.getCurrency().getId()
            );

            response.setCurrencyName(
                    entity.getCurrency().getCurrencyCode()
            );
        }

        // -----------------------------------------------------
        // Transaction Type
        // -----------------------------------------------------

        if (entity.getTransactionType() != null) {

            response.setTransactionType(
                    entity.getTransactionType().name()
            );

            response.setTransactionTypeName(
                    entity.getTransactionType().name()
            );
        }

        // -----------------------------------------------------
        // Transaction Status
        // -----------------------------------------------------

        response.setTransactionStatus(
                entity.getTransactionStatus()
        );

        // -----------------------------------------------------
        // Payment Method
        // -----------------------------------------------------

        if (entity.getPaymentMethod() != null) {

            response.setPaymentMethodId(
                    entity.getPaymentMethod().getId()
            );

            response.setPaymentMethodName(
                    entity.getPaymentMethod()
                            .getPaymentName()
            );
        }

        // -----------------------------------------------------
        // Payment Account
        // -----------------------------------------------------

        response.setMaskedPaymentAccount(
                entity.getMaskedPaymentAccount()
        );

        // -----------------------------------------------------
        // Authorization
        // -----------------------------------------------------

        response.setAuthorizationCode(
                entity.getAuthorizationCode()
        );

        response.setResponseCode(
                entity.getResponseCode()
        );

        response.setResponseMessage(
                entity.getResponseMessage()
        );

        response.setRetrievalReferenceNumber(
                entity.getRetrievalReferenceNumber()
        );

        response.setExternalReferenceNumber(
                entity.getExternalReferenceNumber()
        );

        // -----------------------------------------------------
        // MDR
        // -----------------------------------------------------

        response.setMdrRate(
                entity.getMdrRate()
        );

        response.setMdrAmount(
                entity.getMdrAmount()
        );

        // -----------------------------------------------------
        // Settlement
        // -----------------------------------------------------

        response.setSettlementAmount(
                entity.getSettlementAmount()
        );

        response.setSettlementStatus(
                entity.getSettlementStatus()
        );

        // -----------------------------------------------------
        // Merchant QR
        // -----------------------------------------------------

        if (entity.getMerchantQR() != null) {

            response.setMerchantQRId(
                    entity.getMerchantQR().getId()
            );

            response.setMerchantQRName(
                    entity.getMerchantQR().getQrCode()
            );
        }

        // -----------------------------------------------------
        // Reversal
        // -----------------------------------------------------

        response.setIsReversed(
                Boolean.TRUE.equals(
                        entity.getIsReversed()
                )
        );

        if (Boolean.TRUE.equals(entity.getIsReversed())) {

            response.setReversalDatetime(
                    entity.getReversalDatetime()
            );

            response.setReason(
                    entity.getReason()
            );

            if (entity.getOriginalTransaction() != null) {

                response.setOriginalTransactionNumber(
                        entity.getOriginalTransaction()
                                .getTransactionNumber()
                );
            }
        }

        return response;
    }
}
