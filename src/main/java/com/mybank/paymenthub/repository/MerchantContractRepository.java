package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantContract;
import com.mybank.paymenthub.enums.ContractStatus;
import com.mybank.paymenthub.enums.MerchantStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MerchantContractRepository
        extends JpaRepository<MerchantContract,Long> {
      boolean existsByMerchantIdAndStatus(
              Long merchantId,
              ContractStatus contractStatus
      );

       @Query(
               """
           SELECT CON
           FROM MerchantContract CON
           WHERE
           LOWER(CON.contractNumber) LIKE LOWER(CONCAT('%',:search,'%'))
        """
       )

    Page<MerchantContract> searchMerchantContracts(
            @Param("search") String search,
            Pageable pageable
       );

    Optional<MerchantContract> findMerchantContractByMerchantId(
            Long merchantId
    );
}
