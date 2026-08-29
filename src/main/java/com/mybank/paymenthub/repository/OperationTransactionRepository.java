package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.dto.response.MerchantSummaryResponse;
import com.mybank.paymenthub.entity.OperationTransaction;
import com.mybank.paymenthub.enums.SettlementStatus;
import com.mybank.paymenthub.enums.TransactionStatus;
import com.mybank.paymenthub.repository.projection.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OperationTransactionRepository
        extends JpaRepository<OperationTransaction,Long> {

    Optional<OperationTransaction> findByTransactionNumber(
            String transactionNumber
    );
    @Query(
            """
                SELECT oper FROM OperationTransaction oper
                WHERE oper.transactionNumber like concat('%',:search,'%')
                
            """
    )
    Page<OperationTransaction> findByTransactionNumberContaining(
            @Param("search") String search,
            Pageable pageable
    );
    //Group Merchant
    @Query(
            """
                SELECT Oper FROM OperationTransaction Oper
                WHERE Oper.transactionStatus = :transactionStatus
                AND Oper.settlementStatus = :settlementStatus
                AND Oper.createdDate >= :fromDate
                AND Oper.createdDate < :toDate
            """
    )
    List<OperationTransaction> findEligibleForSettlement(
            @Param("transactionStatus")
            TransactionStatus transactionStatus,

            @Param("settlementStatus")
            SettlementStatus settlementStatus,

            @Param("fromDate")
            LocalDateTime fromDate,

            @Param("toDate")
            LocalDateTime toDate
    );

    @Query("""
    SELECT
        oper.transactionNumber AS transactionNumber,
        oper.transactionTimestamp AS transactionTimestamp,
        oper.referenceNumber AS referenceNumber,

        merchant.merchantNumber AS merchantNumber,
        merchant.merchantName AS merchantName,

        outlet.outletNumber AS outletNumber,
        outlet.outletName AS outletName,

        terminal.terminalNumber AS terminalNumber,

        channel.channelName AS paymentChannel,
        method.paymentName AS paymentMethod,
        currency.currencyCode AS currencyCode,

        oper.transactionType AS transactionType,
        oper.transactionAmount AS transactionAmount,
        oper.mdrRate AS mdrRate,
        oper.mdrAmount AS mdrAmount,
        oper.settlementAmount AS settlementAmount,

        oper.transactionStatus AS transactionStatus,
        oper.settlementStatus AS settlementStatus

    FROM OperationTransaction oper

    JOIN oper.merchantOutlet outlet
    JOIN outlet.merchant merchant

    JOIN oper.terminal terminal
    JOIN oper.paymentChannel channel
    JOIN oper.paymentMethod method
    JOIN oper.currency currency

    WHERE oper.transactionTimestamp >= :fromDate
      AND oper.transactionTimestamp < :toDate

    ORDER BY oper.transactionTimestamp DESC
""")
    Page<TransactionReportProjection> findTransactionReport(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate,
            Pageable pageable
    );

    @Query("""
    SELECT new com.mybank.paymenthub.dto.response.MerchantSummaryResponse(

        merchant.merchantNumber,

        merchant.merchantName,

        COUNT(oper),

        SUM(
            CASE
                WHEN oper.transactionStatus =
                     com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                THEN 1
                ELSE 0
            END
        ),

        SUM(
            CASE
                WHEN oper.transactionStatus =
                     com.mybank.paymenthub.enums.TransactionStatus.FAILED
                THEN 1
                ELSE 0
            END
        ),

        SUM(
            CASE
                WHEN oper.transactionStatus =
                     com.mybank.paymenthub.enums.TransactionStatus.REVERSED
                THEN 1
                ELSE 0
            END
        ),

        SUM(oper.transactionAmount),

        SUM(oper.mdrAmount),

        SUM(oper.settlementAmount)

    )
    FROM OperationTransaction oper

    JOIN oper.merchantOutlet outlet

    JOIN outlet.merchant merchant

    WHERE oper.transactionTimestamp >= :fromDate
      AND oper.transactionTimestamp < :toDate

    GROUP BY
        merchant.merchantNumber,
        merchant.merchantName

    ORDER BY
        merchant.merchantName
""")
    List<MerchantSummaryResponse> getMerchantSummary(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
    SELECT
        t.terminal.terminalNumber AS terminalNumber,
        t.terminal.terminalNumber AS terminalName,

        t.merchantOutlet.merchant.merchantNumber AS merchantNumber,
        t.merchantOutlet.merchant.merchantName AS merchantName,

        COUNT(t) AS totalTransactions,

        SUM(
            CASE
                WHEN t.transactionStatus = 'SUCCESS'
                THEN 1
                ELSE 0
            END
        ) AS successfulTransactions,

        SUM(
            CASE
                WHEN t.transactionStatus = 'FAILED'
                THEN 1
                ELSE 0
            END
        ) AS failedTransactions,

        SUM(
            CASE
                WHEN t.transactionStatus = 'REVERSED'
                THEN 1
                ELSE 0
            END
        ) AS reversedTransactions,

        COALESCE(SUM(t.transactionAmount), 0) AS totalTransactionAmount,

        COALESCE(SUM(t.mdrAmount), 0) AS totalMdrAmount,

        COALESCE(SUM(t.settlementAmount), 0) AS totalSettlementAmount

    FROM OperationTransaction t

    WHERE t.createdDate >= :fromDate
      AND t.createdDate < :toDate

    GROUP BY
        t.terminal.terminalNumber,
        t.terminal.terminalNumber,
        t.merchantOutlet.merchant.merchantNumber,
        t.merchantOutlet.merchant.merchantName

    ORDER BY
        t.terminal.terminalNumber
""")
    List<TerminalSummaryProjection> findTerminalSummary(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
    SELECT
        pm.paymentCode AS paymentCode,
        pm.paymentName AS paymentMethod,

        COUNT(ot.id) AS totalTransactions,

        SUM(
            CASE
                WHEN ot.transactionStatus = com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                THEN 1
                ELSE 0
            END
        ) AS successfulTransactions,

        SUM(
            CASE
                WHEN ot.transactionStatus = com.mybank.paymenthub.enums.TransactionStatus.FAILED
                THEN 1
                ELSE 0
            END
        ) AS failedTransactions,

        SUM(
            CASE
                WHEN ot.transactionStatus = com.mybank.paymenthub.enums.TransactionStatus.REVERSED
                THEN 1
                ELSE 0
            END
        ) AS reversedTransactions,

        COALESCE(
            SUM(
                CASE
                    WHEN ot.transactionStatus = com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                    THEN ot.transactionAmount
                    ELSE 0
                END
            ),
            0
        ) AS totalTransactionAmount,

        COALESCE(
            SUM(
                CASE
                    WHEN ot.transactionStatus = com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                    THEN ot.mdrAmount
                    ELSE 0
                END
            ),
            0
        ) AS totalMdrAmount,

        COALESCE(
            SUM(
                CASE
                    WHEN ot.transactionStatus = com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                    THEN ot.settlementAmount
                    ELSE 0
                END
            ),
            0
        ) AS totalSettlementAmount

    FROM OperationTransaction ot
    JOIN ot.paymentMethod pm

    WHERE ot.transactionTimestamp >= :fromDate
      AND ot.transactionTimestamp < :toDate

    GROUP BY
        pm.paymentCode,
        pm.paymentName

    ORDER BY
        pm.paymentName
""")
    List<PaymentMethodSummaryProjection> findPaymentMethodSummary(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
    SELECT
        pc.channelCode AS channelCode,
        pc.channelName AS paymentChannel,

        COUNT(ot.id) AS totalTransactions,

        SUM(
            CASE
                WHEN ot.transactionStatus =
                    com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                THEN 1
                ELSE 0
            END
        ) AS successfulTransactions,

        SUM(
            CASE
                WHEN ot.transactionStatus =
                    com.mybank.paymenthub.enums.TransactionStatus.FAILED
                THEN 1
                ELSE 0
            END
        ) AS failedTransactions,

        SUM(
            CASE
                WHEN ot.transactionStatus =
                    com.mybank.paymenthub.enums.TransactionStatus.REVERSED
                THEN 1
                ELSE 0
            END
        ) AS reversedTransactions,

        COALESCE(
            SUM(
                CASE
                    WHEN ot.transactionStatus =
                        com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                    THEN ot.transactionAmount
                    ELSE 0
                END
            ),
            0
        ) AS totalTransactionAmount,

        COALESCE(
            SUM(
                CASE
                    WHEN ot.transactionStatus =
                        com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                    THEN ot.mdrAmount
                    ELSE 0
                END
            ),
            0
        ) AS totalMdrAmount,

        COALESCE(
            SUM(
                CASE
                    WHEN ot.transactionStatus =
                        com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                    THEN ot.settlementAmount
                    ELSE 0
                END
            ),
            0
        ) AS totalSettlementAmount

    FROM OperationTransaction ot
    JOIN ot.paymentChannel pc

    WHERE ot.transactionTimestamp >= :fromDate
      AND ot.transactionTimestamp < :toDate

    GROUP BY
        pc.channelCode,
        pc.channelName

    ORDER BY
        pc.channelName
""")
    List<PaymentChannelSummaryProjection> findPaymentChannelSummary(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );

    @Query("""
        SELECT
            s.settlementNumber AS settlementNumber,
            m.merchantNumber AS merchantNumber,
            m.merchantName AS merchantName,
            s.settlementDate AS settlementDate,
            COUNT(t.id) AS transactionCount,
            COALESCE(SUM(t.transactionAmount), 0) AS totalTransactionAmount,
            COALESCE(SUM(t.mdrAmount), 0) AS totalMdrAmount,
            COALESCE(SUM(t.settlementAmount), 0) AS totalSettlementAmount,
            s.status AS settlementStatus

        FROM OperationTransaction t

        JOIN t.settlement s
        JOIN s.merchant m
        
        where s.settlementDate >= :fromDate
        and s.settlementDate <= :toDate

        GROUP BY
            s.settlementNumber,
            m.merchantNumber,
            m.merchantName,
            s.settlementDate,
            s.status

        ORDER BY s.settlementDate DESC
        """)
    List<SettlementReportProjection> getSettlementReport(
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate
    );
    @Query("""
    SELECT
        oper.transactionNumber AS transactionNumber,
        oper.transactionTimestamp AS transactionTimestamp,
        oper.referenceNumber AS referenceNumber,

        merchant.merchantNumber AS merchantNumber,
        merchant.merchantName AS merchantName,

        outlet.outletNumber AS outletNumber,
        outlet.outletName AS outletName,

        terminal.terminalNumber AS terminalNumber,

        channel.channelName AS paymentChannel,
        method.paymentName AS paymentMethod,
        curr.currencyCode AS currency,

        oper.transactionType AS transactionType,
        oper.transactionAmount AS transactionAmount,
        oper.mdrRate AS mdrRate,
        oper.mdrAmount AS mdrAmount,
        oper.settlementAmount AS settlementAmount,

        oper.transactionStatus AS transactionStatus,
        oper.settlementStatus AS settlementStatus

    FROM OperationTransaction oper

    JOIN oper.merchantOutlet outlet
    JOIN outlet.merchant merchant
    JOIN oper.terminal terminal
    JOIN oper.paymentChannel channel
    JOIN oper.paymentMethod method
    JOIN oper.currency curr

    WHERE oper.transactionTimestamp >= :fromDate
      AND oper.transactionTimestamp < :toDate

    ORDER BY oper.transactionTimestamp DESC
""")
    List<TransactionReportProjection> findTransactionReportForExport(
            @Param("fromDate") LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
    );
    @Query(
            """
              select
                 count(ot.id) as totalTransactions,
                 sum(
                    case 
                    when ot.transactionStatus = 
                    com.mybank.paymenthub.enums.TransactionStatus.SUCCESS
                    then 1
                    else 0 end 
                 ) as successfulTransactions,
                 sum(
                    case 
                    when ot.transactionStatus =
                    com.mybank.paymenthub.enums.TransactionStatus.FAILED
                    then 1 
                    else 0 end 
                 ) as failTransactions,
                 sum(
                    case 
                    when ot.transactionStatus =
                    com.mybank.paymenthub.enums.TransactionStatus.PENDING
                    then 1 
                    else 0 end 
                 ) as pendingTransactions,
                 sum(
                   case 
                   when ot.transactionStatus =
                   com.mybank.paymenthub.enums.TransactionStatus.REVERSED
                   then 1
                   else 0 end 
                 ) as reversedTransactions,
                 COALESCE(
                    SUM(ot.transactionAmount) , 0
                 ) as totalTransactionAmount,
                 COALESCE(
                    SUM(ot.mdrAmount) , 0
                 ) as totalMDRAmount,
                 COALESCE(
                    SUM(ot.settlementAmount) , 0
                 ) as totalSettlementAmount
              from OperationTransaction ot
              where ot.transactionTimestamp >= :fromDate
              and ot.transactionTimestamp <= :toDate
         """
    ) DashboardProjection getDashboardSummary(
           @Param("fromDate") LocalDateTime fromDate,
           @Param("toDate") LocalDateTime toDate
    );

}
