package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantOutlet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantOutletRepository
        extends JpaRepository<MerchantOutlet,Long> {
    boolean existsByOutletName(String outletName);
    boolean existsByOutletNameAndIdNot(String outletName,Long id);

   @Query(
           """
            SELECT mo FROM MerchantOutlet mo
            JOIN mo.merchant merchant
            WHERE
                LOWER(mo.outletName)
                LIKE lOWER(CONCAT('%',:search,'%')
                )
         """
           )
           Page<MerchantOutlet> searchMerchantOutlet(
                   @Param("search") String search,
                   Pageable pageable
           );
}
