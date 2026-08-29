package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantBankAccount;
import com.mybank.paymenthub.enums.AccountStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MerchantBankAccountRepository extends JpaRepository<MerchantBankAccount,Long> {
    Optional<MerchantBankAccount> findByMerchantIdAndCurrencyIdAndStatus(
            Long merchantId,
            Long currencyId,
            AccountStatus status
    );
}
