package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantOutletMerchantHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantOutletMerchantHistoryRepository
        extends JpaRepository<
        MerchantOutletMerchantHistory,
        Long
        > {

    Optional<MerchantOutletMerchantHistory>
    findByMerchantOutletIdAndEffectiveToIsNull(
            Long merchantOutletId
    );

    Page<MerchantOutletMerchantHistory>
    findByMerchantOutletId(
            Long merchantOutletId,
            Pageable pageable
    );
}