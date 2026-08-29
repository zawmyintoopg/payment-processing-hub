package com.mybank.paymenthub.repository;


import com.mybank.paymenthub.dto.response.PaymentChannelSummaryResponse;
import com.mybank.paymenthub.entity.PaymentChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface PaymentChannelRepository
        extends JpaRepository<PaymentChannel,Long> {

    @Query("""
     select ot.paymentChannel.channelCode,
      ot.paymentChannel.channelName,
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
                 ) as reverseTransactions,
                 COALESCE(
                    SUM(ot.transactionAmount) , 0
                 ) as totalTransactionAmount,
                 COALESCE(
                    SUM(ot.mdrAmount) , 0
                 ) as totalMDRAmount,
                 COALESCE(
                    SUM(ot.settlementAmount) , 0
                 ) as totalSettlementAmount 
     from
     OperationTransaction ot 
     where ot.transactionTimestamp >= :fromDate
     and ot.transactionTimestamp <= :toDate
     group by ot.paymentChannel.channelCode
""")
    PaymentChannelSummaryResponse getPaymentChannelReport(
            @Param("fromDate")LocalDateTime fromDate,
            @Param("toDate") LocalDateTime toDate
            );
}
