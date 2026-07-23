package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantOutlet;
import com.mybank.paymenthub.enums.MerchantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MerchantOutletRepository extends JpaRepository<MerchantOutlet,Long> {
    // search by outlet number
    Optional<MerchantOutlet> findByOutletNumber(String outletNumber);

    // search by marchant id
    List<MerchantOutlet> findByMerchantId(Long merchantId);

    // search by merchant status
    List<MerchantOutlet> findByStatus(MerchantStatus status);

    //check duplicate outlet name and merchant id
    boolean existsByOutletNameAndMerchantId(
            String outletName,
            Long merchantId
    );

    // check duplicate outlet name and merchant id exist or not
    boolean existsByOutletNameAndMerchantIdAndIdNot(
            String outletName,
            Long merchantId,
            Long id
    );


}
