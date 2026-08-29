package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.SettlementDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementDetailsRepository
        extends JpaRepository<SettlementDetail,Long> {

    List<SettlementDetail> findBySettlementId(
            Long settlementId
    );
}
