package com.mybank.paymenthub.service.impl;

import com.mybank.paymenthub.dto.request.OperationTransactionRequest;
import com.mybank.paymenthub.dto.request.PaymentCallBackRequest;
import com.mybank.paymenthub.dto.request.ProviderReversalRequest;
import com.mybank.paymenthub.dto.request.ReversalRequest;
import com.mybank.paymenthub.dto.response.OperationTransactionResponse;
import com.mybank.paymenthub.dto.response.ProviderReversalResponse;
import com.mybank.paymenthub.entity.Currency;
import com.mybank.paymenthub.entity.Merchant;
import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.entity.OperationTransaction;
import com.mybank.paymenthub.entity.PaymentChannel;
import com.mybank.paymenthub.entity.PaymentMethod;
import com.mybank.paymenthub.entity.TerminalAssignment;
import com.mybank.paymenthub.entity.TerminalInventory;
import com.mybank.paymenthub.enums.TerminalAssignmentStatus;
import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.enums.TransactionType;
import com.mybank.paymenthub.exception.BusinessException;
import com.mybank.paymenthub.exception.ResourceNotFoundException;
import com.mybank.paymenthub.mapper.OperationTransactionMapper;
import com.mybank.paymenthub.repository.CurrencyRepository;
import com.mybank.paymenthub.repository.OperationTransactionRepository;
import com.mybank.paymenthub.repository.PaymentChannelRepository;
import com.mybank.paymenthub.repository.PaymentMethodRepository;
import com.mybank.paymenthub.repository.TerminalAssignmentRepository;
import com.mybank.paymenthub.repository.TerminalInventoryRepository;
import com.mybank.paymenthub.service.NumberSequenceService;
import com.mybank.paymenthub.service.OperationTransactionService;
import com.mybank.paymenthub.service.PaymentProviderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class OperationTransactionServiceImpl
        implements OperationTransactionService {

    private final OperationTransactionRepository operationTransactionRepository;
    private final OperationTransactionMapper operationTransactionMapper;

    private final TerminalInventoryRepository terminalInventoryRepository;
    private final TerminalAssignmentRepository terminalAssignmentRepository;

    private final CurrencyRepository currencyRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PaymentChannelRepository paymentChannelRepository;

    private final NumberSequenceService numberSequenceService;
    private final PaymentProviderService paymentProviderService;


    // ======================================================
    // CREATE PAYMENT TRANSACTION
    // ======================================================

    @Override
    public OperationTransactionResponse create(
            OperationTransactionRequest request
    ) {

        // 1. Find Terminal
        TerminalInventory terminal =
                findTerminalInventoryById(
                        request.getTerminalId()
                );


        // 2. Find active Terminal Assignment
        TerminalAssignment assignment =
                terminalAssignmentRepository
                        .findByTerminalInventoryIdAndStatus(
                                terminal.getId(),
                                TerminalAssignmentStatus.ASSIGNED
                        )
                        .orElseThrow(() ->
                                new BusinessException(
                                        "Terminal is not actively assigned"
                                )
                        );


        // 3. Get Merchant Outlet from assignment
        MerchantOutlet outlet =
                assignment.getMerchantOutlet();

        if (outlet == null) {
            throw new BusinessException(
                    "Terminal assignment has no merchant outlet"
            );
        }


        // 4. Get Merchant from outlet
        Merchant merchant =
                outlet.getMerchant();

        if (merchant == null) {
            throw new BusinessException(
                    "Merchant outlet has no merchant"
            );
        }


        // 5. Find Currency
        Currency currency =
                findCurrencyById(
                        request.getCurrencyId()
                );


        // 6. Find Payment Method
        PaymentMethod paymentMethod =
                findPaymentMethodById(
                        request.getPaymentMethodId()
                );


        // 7. Find Payment Channel
        PaymentChannel paymentChannel =
                findPaymentChannelById(
                        request.getPaymentChannelId()
                );


        // 8. Convert Request -> Entity
        OperationTransaction entity =
                operationTransactionMapper.toEntity(
                        request,
                        terminal,
                        outlet,
                        merchant,
                        currency,
                        paymentMethod,
                        paymentChannel
                );


        // 9. Generate internal transaction number
        entity.setTransactionNumber(
                numberSequenceService
                        .generateTransactionNumber()
        );


        // 10. Set transaction timestamp
        entity.setTransactionTimestamp(
                LocalDateTime.now()
        );


        // 11. Save transaction
        OperationTransaction saved =
                operationTransactionRepository.save(
                        entity
                );


        // 12. Convert Entity -> Response
        return operationTransactionMapper.toResponse(
                saved
        );
    }


    // ======================================================
    // PAYMENT CALLBACK
    // ======================================================

    @Override
    public OperationTransactionResponse processPaymentCallBack(
            String transactionNumber,
            PaymentCallBackRequest request
    ) {

        // 1. Find transaction
        OperationTransaction transaction =
                findByTransactionNumber(
                        transactionNumber
                );


        // 2. Check if already successful
        if (transaction.getTransactionStatus()
                == TransactionStatus.SUCCESS) {

            throw new BusinessException(
                    "Transaction has already been completed"
            );
        }


        // 3. Provider reference
        String providerReferenceNumber =
                request.getProviderReferenceNumber();


        // 4. Duplicate callback check
        if (providerReferenceNumber != null
                && providerReferenceNumber.equals(
                transaction.getProviderReferenceNumber()
        )) {

            throw new BusinessException(
                    "Duplicate payment callback"
            );
        }


        // 5. Update callback information
        transaction.setProviderReferenceNumber(
                providerReferenceNumber
        );

        transaction.setTransactionStatus(
                request.getTransactionStatus()
        );

        transaction.setAuthorizationCode(
                request.getAuthorizationCode()
        );

        transaction.setResponseCode(
                request.getResponseCode()
        );

        transaction.setResponseMessage(
                request.getResponseMessage()
        );

        transaction.setRetrievalReferenceNumber(
                request.getRetrievalReferenceNumber()
        );


        // Transaction is managed by @Transactional
        // No explicit save is required.

        return operationTransactionMapper.toResponse(
                transaction
        );
    }


    // ======================================================
    // QUERY
    // ======================================================

    @Override
    public Page<OperationTransactionResponse> getAll(
            String search,
            Pageable pageable
    ) {

        Page<OperationTransaction> transactions;

        if (search == null || search.isBlank()) {

            transactions =
                    operationTransactionRepository
                            .findAll(pageable);

        } else {

            transactions =
                    operationTransactionRepository
                            .findByTransactionNumberContaining(
                                    search,
                                    pageable
                            );
        }


        return transactions.map(
                operationTransactionMapper::toResponse
        );
    }


    @Override
    public OperationTransactionResponse getById(
            Long transactionId
    ) {

        OperationTransaction transaction =
                operationTransactionRepository
                        .findById(transactionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Operation Transaction Not Found",
                                        "transactionId",
                                        transactionId
                                )
                        );

        return operationTransactionMapper.toResponse(
                transaction
        );
    }


    @Override
    public OperationTransactionResponse getByTransactionNumber(
            String transactionNumber
    ) {

        return operationTransactionMapper.toResponse(
                findByTransactionNumber(
                        transactionNumber
                )
        );
    }


    // ======================================================
    // REVERSAL REQUEST
    // ======================================================

    @Override
    public OperationTransactionResponse requestReversal(
            String transactionNumber,
            ReversalRequest request
    ) {

        // 1. Find original transaction
        OperationTransaction originalTransaction =
                findByTransactionNumber(
                        transactionNumber
                );


        // 2. Check already reversed
        if (Boolean.TRUE.equals(
                originalTransaction.getIsReversed()
        )) {

            throw new BusinessException(
                    "Transaction has already been reversed"
            );
        }


        // 3. Transaction must be SUCCESS
        if (originalTransaction.getTransactionStatus()
                != TransactionStatus.SUCCESS) {

            throw new BusinessException(
                    "Transaction reversal can only be performed "
                            + "on a successful transaction"
            );
        }


        // 4. Cannot reverse another reversal
        if (originalTransaction.getTransactionType()
                == TransactionType.REVERSAL) {

            throw new BusinessException(
                    "Reversal transaction cannot be reversed"
            );
        }


        // 5. Provider reference is required
        if (originalTransaction
                .getProviderReferenceNumber() == null
                || originalTransaction
                .getProviderReferenceNumber()
                .isBlank()) {

            throw new BusinessException(
                    "Provider reference number is required for reversal"
            );
        }


        // 6. Create reversal transaction
        OperationTransaction reversalTransaction =
                createReversalTransaction(
                        originalTransaction,
                        request
                );


        // 7. Save REVERSAL_PENDING
        reversalTransaction =
                operationTransactionRepository.save(
                        reversalTransaction
                );


        // 8. Create provider request
        ProviderReversalRequest providerRequest =
                new ProviderReversalRequest();

        providerRequest.setProviderReferenceNumber(
                originalTransaction
                        .getProviderReferenceNumber()
        );

        providerRequest.setReason(
                request.getReason()
        );


        // 9. Send reversal request to provider
        ProviderReversalResponse providerResponse =
                paymentProviderService.reverse(
                        providerRequest
                );


        // 10. Save provider reversal reference
        reversalTransaction.setProviderReferenceNumber(
                providerResponse
                        .getProviderReferenceNumber()
        );

        reversalTransaction.setTransactionStatus(
                TransactionStatus.REVERSAL_PENDING
        );


        // 11. Return
        return operationTransactionMapper.toResponse(
                reversalTransaction
        );
    }


    // ======================================================
    // REVERSAL CALLBACK
    // ======================================================

    @Override
    public OperationTransactionResponse processReversalCallBack(
            String transactionNumber,
            PaymentCallBackRequest request
    ) {

        // 1. Find reversal transaction
        OperationTransaction reversal =
                findByTransactionNumber(
                        transactionNumber
                );


        // 2. Must be REVERSAL
        if (reversal.getTransactionType()
                != TransactionType.REVERSAL) {

            throw new BusinessException(
                    "Transaction is not a reversal transaction"
            );
        }


        // 3. Find original transaction
        OperationTransaction original =
                reversal.getOriginalTransaction();

        if (original == null) {

            throw new BusinessException(
                    "Original transaction not found"
            );
        }


        // 4. Provider reference
        String providerReferenceNumber =
                request.getProviderReferenceNumber();


        // 5. Duplicate callback check
        if (providerReferenceNumber != null
                && providerReferenceNumber.equals(
                reversal.getProviderReferenceNumber()
        )) {

            throw new BusinessException(
                    "Duplicate reversal callback"
            );
        }


        // 6. Update provider reference
        reversal.setProviderReferenceNumber(
                providerReferenceNumber
        );


        // 7. Provider callback SUCCESS
        if (request.getTransactionStatus()
                == TransactionStatus.SUCCESS) {

            // Reversal transaction
            reversal.setTransactionStatus(
                    TransactionStatus.REVERSED
            );


            // Original transaction
            original.setIsReversed(true);

            original.setReversalDatetime(
                    LocalDateTime.now()
            );

        } else {

            // Reversal failed/rejected
            reversal.setTransactionStatus(
                    request.getTransactionStatus()
            );
        }


        // Both entities are managed by @Transactional.
        // No explicit save is required.

        return operationTransactionMapper.toResponse(
                reversal
        );
    }


    // ======================================================
    // FIND HELPERS
    // ======================================================

    private PaymentChannel findPaymentChannelById(
            Long id
    ) {

        return paymentChannelRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment Channel Not Found",
                                "id",
                                id
                        )
                );
    }


    private TerminalInventory findTerminalInventoryById(
            Long id
    ) {

        return terminalInventoryRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Terminal Not Found",
                                "id",
                                id
                        )
                );
    }


    private Currency findCurrencyById(
            Long id
    ) {

        return currencyRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Currency Not Found",
                                "id",
                                id
                        )
                );
    }


    private PaymentMethod findPaymentMethodById(
            Long paymentMethodId
    ) {

        return paymentMethodRepository
                .findById(paymentMethodId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Payment Method Not Found",
                                "paymentMethodId",
                                paymentMethodId
                        )
                );
    }


    private OperationTransaction findByTransactionNumber(
            String transactionNumber
    ) {

        return operationTransactionRepository
                .findByTransactionNumber(
                        transactionNumber
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction Not Found",
                                "transactionNumber",
                                transactionNumber
                        )
                );
    }


    // ======================================================
    // CREATE REVERSAL TRANSACTION
    // ======================================================

    private OperationTransaction createReversalTransaction(
            OperationTransaction original,
            ReversalRequest request
    ) {

        OperationTransaction reversal =
                new OperationTransaction();


        // 1. Generate new transaction number
        reversal.setTransactionNumber(
                numberSequenceService
                        .generateTransactionNumber()
        );


        // 2. Transaction type
        reversal.setTransactionType(
                TransactionType.REVERSAL
        );


        // 3. Link original transaction
        reversal.setOriginalTransaction(
                original
        );


        // 4. Reversal amount
        reversal.setTransactionAmount(
                original.getTransactionAmount()
        );


        // 5. Initial status
        reversal.setTransactionStatus(
                TransactionStatus.REVERSAL_PENDING
        );


        // 6. Transaction timestamp
        reversal.setTransactionTimestamp(
                LocalDateTime.now()
        );


        // 7. Copy transaction relationships
        reversal.setPaymentChannel(
                original.getPaymentChannel()
        );

        reversal.setTerminal(
                original.getTerminal()
        );

        reversal.setMerchant(
                original.getMerchant()
        );

        reversal.setMerchantOutlet(
                original.getMerchantOutlet()
        );

        reversal.setCurrency(
                original.getCurrency()
        );

        reversal.setPaymentMethod(
                original.getPaymentMethod()
        );


        // 8. Generate new reference number
        reversal.setReferenceNumber(
                numberSequenceService
                        .generateTransactionRef()
        );


        // 9. Reversal reason
        reversal.setReason(
                request.getReason()
        );


        // 10. Copy optional payment information
        reversal.setMaskedPaymentAccount(
                original.getMaskedPaymentAccount()
        );

        reversal.setMerchantQR(
                original.getMerchantQR()
        );


        // 11. Initial reversal flag
        reversal.setIsReversed(false);


        return reversal;
    }
}

