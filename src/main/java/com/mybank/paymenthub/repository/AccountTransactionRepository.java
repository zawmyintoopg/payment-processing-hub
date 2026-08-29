package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository
        extends JpaRepository<AccountTransaction,Long> {

}
