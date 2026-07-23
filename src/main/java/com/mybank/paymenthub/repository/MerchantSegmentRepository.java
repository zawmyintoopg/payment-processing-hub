package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantSegment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface MerchantSegmentRepository extends JpaRepository<MerchantSegment,Long> {
    Optional<MerchantSegment> findByMerchantSegmentCode(String merchantSegmentCode);

    Optional<MerchantSegment> findByMerchantSegmentName(String merchantSegmentName);

    boolean existsByMerchantSegmentCode(String merchantSegmentCode);

    boolean existsByMerchantSegmentName(String merchantSegmentName);

    boolean existsByMerchantSegmentCodeAndId(String merchantSegmentCode, Long id);


}
