package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.Bank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRepository
        extends JpaRepository<Bank, Long> {

    boolean existsByBankCode(String bankCode);

    boolean existsByBankName(String bankName);
}