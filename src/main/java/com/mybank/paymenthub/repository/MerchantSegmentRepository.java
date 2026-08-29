package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantSegment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MerchantSegmentRepository
        extends JpaRepository<MerchantSegment,Long> {

    Optional<MerchantSegment> findByMerchantSegmentCode(
            String merchantSegmentCode
    );

    Optional<MerchantSegment> findByMerchantSegmentName(
            String merchantSegmentName
    );

    boolean existsByMerchantSegmentCode(
            String merchantSegmentCode
    );

    boolean existsByMerchantSegmentName(
            String merchantSegmentName
    );

    boolean existsByMerchantSegmentCodeAndId(
            String merchantSegmentCode, Long id
    );

    boolean existsByMerchantSegmentNameAndId(
            String merchantSegmentName, Long id
    );

    @Query(
            """        
              select ms from MerchantSegment ms
                          where ms.merchantSegmentCode = :search
            """
    )
    Page<MerchantSegment> searchMerchantSegmentCode(
            @Param("search") String search,
            Pageable pageable
    );
}
