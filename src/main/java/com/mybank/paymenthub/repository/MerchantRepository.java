package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant,Long> {
    List<Merchant> findByMerchantNameContainingIgnoreCase(String name);

    Optional<Merchant> findByMerchantNumber(String merchantNumber);

    boolean existsByMerchantRegistrationNo(String registrationNo);

    boolean existsByMerchantNumber(String merchantNumber);

    boolean existsByMerchantRegistrationNoAndIdNot(String registrationNo, Long id);

    boolean existsByMerchantNumberAndIdNot(String merchantNumber,Long id);


}
