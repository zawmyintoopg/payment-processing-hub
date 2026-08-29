    package com.mybank.paymenthub.service.impl;

    import com.mybank.paymenthub.dto.response.SettlementProcessResponse;
    import com.mybank.paymenthub.dto.response.SettlementResponse;
    import com.mybank.paymenthub.entity.*;
    import com.mybank.paymenthub.enums.AccountStatus;
    import com.mybank.paymenthub.enums.AccountTransactionType;
    import com.mybank.paymenthub.enums.SettlementStatus;
    import com.mybank.paymenthub.enums.TransactionStatus;
    import com.mybank.paymenthub.exception.ResourceNotFoundException;
    import com.mybank.paymenthub.repository.*;
    import com.mybank.paymenthub.service.NumberSequenceService;
    import com.mybank.paymenthub.service.SettlementGroupKey;
    import com.mybank.paymenthub.service.SettlementService;
    import jakarta.transaction.Transactional;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.time.LocalDateTime;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @Service
    @RequiredArgsConstructor
    public class SettlementServiceImpl implements SettlementService {

        private final OperationTransactionRepository operationTransactionRepository;
        private final NumberSequenceService numberSequenceService;
        private final MerchantBankAccountRepository merchantBankAccountRepository;
        private final SettlementRepository settlementRepository;
        private final SettlementDetailsRepository settlementDetailsRepository;
        private final AccountTransactionRepository accountTransactionRepository;
        @Override
        @Transactional
        public SettlementProcessResponse createSettlementProcess() {
            // =====================================================
            // STEP 1: Date Range
            // =====================================================
            LocalDate yesterday =
                    LocalDate.now().minusDays(8);
            LocalDateTime fromDate =
                    yesterday.atStartOfDay();
            LocalDateTime toDate =
                    yesterday.plusDays(4).atStartOfDay();
            List<OperationTransaction> transactions =
                    operationTransactionRepository.findEligibleForSettlement(
                            TransactionStatus.SUCCESS,
                            SettlementStatus.CREATED,
                            fromDate,
                            toDate
                    );
            Map<SettlementGroupKey, List<OperationTransaction>> groups =
                    new HashMap<>();

            for (OperationTransaction transaction : transactions) {

                Long merchantId =
                        transaction.getMerchantOutlet()
                                .getMerchant()
                                .getId();

                Long currencyId =
                        transaction.getCurrency()
                                .getId();

                SettlementGroupKey key =
                        new SettlementGroupKey(
                                merchantId,
                                currencyId
                        );

                if (groups.containsKey(key)) {

                    groups.get(key)
                            .add(transaction);

                } else {

                    List<OperationTransaction> newGroup =
                            new ArrayList<>();

                    newGroup.add(transaction);

                    groups.put(
                            key,
                            newGroup
                    );
                }
            }

            List<SettlementResponse> settlementResponses =
                    new ArrayList<>();
            int totalTransactionCount = 0;
            BigDecimal totalAmount = BigDecimal.ZERO;
            // =====================================================
            // STEP 5: Process Each Group
            // =====================================================
            for ( Map.Entry<SettlementGroupKey,List<OperationTransaction>>
                    entry : groups.entrySet()
            ) {
                SettlementGroupKey settlementGroupKey =
                        entry.getKey();
                Long merchantId =
                        settlementGroupKey.merchantId();
                Long currencyId =
                        settlementGroupKey.currencyId();
                // -------------------------------------------------
                // Get Transactions
                // -------------------------------------------------
                List<OperationTransaction> groupTransaction =
                        entry.getValue();
                // -------------------------------------------------
                // Calculate Total Amount
                // -------------------------------------------------
                BigDecimal totalSettlementAmount = BigDecimal.ZERO;

                for (
                        OperationTransaction operationTransaction
                        : groupTransaction
                ) {

                    totalSettlementAmount =
                            totalSettlementAmount.add(
                                    operationTransaction
                                            .getSettlementAmount()
                            );
                }

                // -------------------------------------------------
                // Get Merchant
                // -------------------------------------------------
                Merchant merchant =
                        groupTransaction
                                .get(0)
                                .getMerchantOutlet()
                                .getMerchant();
                // -------------------------------------------------
                // Get Active Merchant Bank Account
                // -------------------------------------------------
                MerchantBankAccount merchantBankAccount =
                        merchantBankAccountRepository
                                .findByMerchantIdAndCurrencyIdAndStatus(
                                        merchantId,
                                        currencyId,
                                        AccountStatus.ACTIVE
                                )
                                .orElseThrow(
                                        () -> new ResourceNotFoundException(
                                                "Merchant Bank Account Not Found !",
                                                "merchantId",
                                                merchantId
                                        )
                                );
                // -------------------------------------------------
                // Create Settlement
                // -------------------------------------------------
                Settlement settlement = new Settlement();

                settlement.setSettlementNumber(
                        "set_" +
                                numberSequenceService
                                        .generateSettlementNumber()
                );
                settlement.setMerchant(
                        merchant
                );

                settlement.setSettlementDate(
                        LocalDate.now()
                );

                settlement.setMerchantBankAccount(
                        merchantBankAccount
                );

                settlement.setTotalAmount(
                        totalSettlementAmount
                );

                settlement.setTransactionCount(
                        groupTransaction.size()
                );

                settlement.setStatus(
                        SettlementStatus.CREATED
                );
                System.out.println(
                        "Merchant ID = " + merchantId
                );

                System.out.println(
                        "Currency ID = " + currencyId
                );

                System.out.println(
                        "Account Status = " + AccountStatus.ACTIVE
                );
                // -------------------------------------------------
                // Save Settlement
                // -------------------------------------------------

                settlementRepository.save(
                        settlement
                );
                // =================================================
                // STEP 6: Create Settlement Response
                // =================================================

                SettlementResponse settlementResponse =
                        new SettlementResponse();

                settlementResponse.setSettlementNumber(
                        settlement.getSettlementNumber()
                );

                settlementResponse.setMerchantId(
                        merchant.getId()
                );

                settlementResponse.setMerchantBankAccountId(
                        merchantBankAccount.getId()
                );

                settlementResponse.setSettlementDate(
                        settlement.getSettlementDate()
                );

                settlementResponse.setTotalAmount(
                        settlement.getTotalAmount()
                );

                settlementResponse.setTransactionCount(
                        settlement.getTransactionCount()
                );

                settlementResponse.setStatus(
                        settlement.getStatus()
                );

                settlementResponses.add(
                        settlementResponse
                );

                // =================================================
                // STEP 7: Update Transactions
                // =================================================
                for (OperationTransaction operationTransaction : groupTransaction) {

                    SettlementDetail settlementDetail =
                            new SettlementDetail();

                    settlementDetail.setSettlement(
                            settlement
                    );
                    settlementDetail.setTransaction(
                            operationTransaction
                    );
                    settlementDetail.setSettlementAmount(
                            operationTransaction.getSettlementAmount()
                    );
                    settlementDetailsRepository.save(
                            settlementDetail
                    );
                    // Link Transaction → Settlement
                    operationTransaction.setSettlement(
                            settlement
                    );
                    // Update Settlement Status
                    operationTransaction.setSettlementStatus(
                            SettlementStatus.PROCESSING
                    );
                }
                // =================================================
                // STEP 8: Calculate Overall Result
                // =================================================

                totalTransactionCount +=
                        groupTransaction.size();

                totalAmount =
                        totalAmount.add(
                                totalSettlementAmount
                        );
            }
                // =====================================================
                // STEP 9: Create Process Response
                // =====================================================

                SettlementProcessResponse response = new SettlementProcessResponse();

                response.setSettlementCount(
                        settlementResponses.size()
                );

                response.setTransactionCount(
                        totalTransactionCount
                );

                response.setTotalAmount(
                        totalAmount
                );

                response.setSettlements(
                        settlementResponses
                );

                return response;
            }
        @Override
        @Transactional
        public void completeSettlementProcess() {

            List<Settlement> settlements =
                    settlementRepository.findByStatus(
                            SettlementStatus.PROCESSING
                    );

            for (Settlement settlement : settlements) {

                // ==========================================
                // STEP 1: Get Merchant Bank Account
                // ==========================================

                MerchantBankAccount merchantBankAccount =
                        merchantBankAccountRepository.findById(
                                settlement.getMerchantBankAccount().getId()
                        ).orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Merchant Bank Account Not Found",
                                        "merchantBankAccountId",
                                        settlement.getMerchantBankAccount().getId()
                                )
                        );

                // ==========================================
                // STEP 2: Calculate Balance
                // ==========================================

                BigDecimal balanceBefore =
                        merchantBankAccount.getBalance();

                BigDecimal settlementAmount =
                        settlement.getTotalAmount();

                BigDecimal balanceAfter =
                        balanceBefore.add(settlementAmount);

                // ==========================================
                // STEP 3: Create Account Transaction
                // ==========================================

                AccountTransaction accountTransaction =
                        new AccountTransaction();

                accountTransaction.setTransactionNumber(
                        numberSequenceService.generateTransactionNumber()
                );

                accountTransaction.setTransactionDate(
                        LocalDate.now()
                );

                accountTransaction.setAccountTransactionType(
                        AccountTransactionType.Credit
                );

                accountTransaction.setAmount(
                        settlementAmount
                );

                accountTransaction.setMerchantBankAccount(
                        merchantBankAccount
                );

                accountTransaction.setSettlement(
                        settlement
                );

                accountTransaction.setBalance_before(
                        balanceBefore
                );

                accountTransaction.setBalance_after(
                        balanceAfter
                );

                accountTransactionRepository.save(
                        accountTransaction
                );

                // ==========================================
                // STEP 4: Update Merchant Bank Account
                // ==========================================

                merchantBankAccount.setBalance(
                        balanceAfter
                );

                // ==========================================
                // STEP 5: Complete Transactions
                // ==========================================

                List<SettlementDetail> settlementDetails =
                        settlementDetailsRepository.findBySettlementId(
                                settlement.getId()
                        );

                for (SettlementDetail detail : settlementDetails) {

                    OperationTransaction transaction =
                            detail.getTransaction();

                    transaction.setSettlementStatus(
                            SettlementStatus.COMPLETED
                    );
                }

                // ==========================================
                // STEP 6: Complete Settlement
                // ==========================================

                settlement.setStatus(
                        SettlementStatus.COMPLETED
                );
            }
        }
    }