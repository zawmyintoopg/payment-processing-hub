package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.Settlement;
import com.mybank.paymenthub.enums.SettlementStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SettlementRepository
        extends JpaRepository<Settlement,Long> {

    List<Settlement> findByStatus(
        SettlementStatus status
    );
}
