package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantOwnerRepository extends JpaRepository<MerchantOwner, Long> {
    boolean existsByOwnerName(String ownerName);

    boolean existsByRegistrationNo(String registrationNo);

    boolean existsByEmail(String email);

    Optional<MerchantOwner> findByOwnerNumber(String ownerNumber);

    // for update

    boolean existsByOwnerNameAndIdNot(
            String ownerName, Long id
    );

    boolean existsByRegistrationNoAndIdNot(
            String ownerRegistrationNo,Long id
    );

    boolean existsByEmailAndIdNot(
            String email, Long id
    );
}
