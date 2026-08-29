package com.mybank.paymenthub.service;

import com.mybank.paymenthub.dto.response.OperationTransactionResponse;
import com.mybank.paymenthub.dto.response.SettlementProcessResponse;
import com.mybank.paymenthub.dto.response.SettlementResponse;
import org.springframework.data.domain.Page;

public interface SettlementService {
   SettlementProcessResponse createSettlementProcess();
   void  completeSettlementProcess();

}
