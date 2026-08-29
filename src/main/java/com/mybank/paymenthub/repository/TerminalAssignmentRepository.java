package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.TerminalAssignment;
import com.mybank.paymenthub.enums.TerminalAssignmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TerminalAssignmentRepository
        extends JpaRepository<TerminalAssignment,Long> {

    boolean existsByMerchantOutlet_IdAndTerminalInventory_Id(
            Long merchantOutletId,
            Long terminalInventoryId
    );
    boolean existsByMerchantOutlet_IdAndTerminalInventory_IdAndIdNot(
            Long merchantOutletId,
            Long terminalInventoryId,
            Long id
    );

    @Query(
    """
        SELECT TA
        FROM TerminalAssignment TA
        WHERE LOWER(TA.terminalAssignmentNumber) LIKE LOWER(CONCAT('%',:search,'%'))
            OR
              LOWER(TA.merchantOutlet.outletName) LIKE LOWER(CONCAT('%',:search,'%'))
            OR
              LOWER(TA.terminalInventory.terminalSerialNumber) LIKE LOWER(CONCAT('%',:search,'%'))
    """
    )
    Page<TerminalAssignment> searchTerminalAssignmentNumber(
            String search,
            Pageable pageable
    );

    Optional<TerminalAssignment>
    findByTerminalInventoryIdAndStatus(
            Long terminalInventoryId,
            TerminalAssignmentStatus status
    );

}
