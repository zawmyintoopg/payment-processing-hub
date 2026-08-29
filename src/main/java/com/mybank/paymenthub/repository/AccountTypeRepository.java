package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository
        extends JpaRepository<AccountType, Long> {

    boolean existsByAccountType(String accountType);
}