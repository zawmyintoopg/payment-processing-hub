package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.TerminalInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface TerminalInventoryRepository
        extends JpaRepository<TerminalInventory,Long> {
    @Query(
            """
                SELECT INV
                FROM TerminalInventory INV
                WHERE
                  LOWER(INV.terminalNumber) LIKE LOWER(CONCAT('%',:search,'%'))
                                        OR
                  LOWER(INV.terminalSerialNumber) LIKE LOWER(CONCAT('%',:search,'%'))
            """
    )
    Page<TerminalInventory> searchTerminalNumberOrTerminalSerialNumber(
            String search,
            Pageable pageable
    );

    boolean existsByTerminalNumberAndTerminalSerialNumber(
            String terminalNumber,
            String terminalSerialNumber
    );

    boolean existsByTerminalNumberAndTerminalSerialNumberAndIdNot(
            String terminalNumber,
            String terminalSerialNumber,
            Long id
    );
}
