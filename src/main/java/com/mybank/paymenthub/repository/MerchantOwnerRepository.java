package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantOwner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MerchantOwnerRepository
        extends JpaRepository<MerchantOwner,Long> {
    boolean existsByOwnerName(String ownerName);
    boolean existsByRegistrationNo(String registrationNo);
    boolean existsByEmail(String email);
    boolean existsByOwnerNameAndIdNot(
            String ownerName, Long id
    );
    boolean existsByRegistrationNoAndIdNot(
            String ownerRegistrationNo,Long id
    );
    boolean existsByEmailAndIdNot(
            String email, Long id
    );
    @Query("""
    SELECT m
    FROM MerchantOwner m
    WHERE
        LOWER(m.ownerNumber) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(m.ownerName) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(m.registrationNo) LIKE LOWER(CONCAT('%', :search, '%'))
        OR LOWER(m.email) LIKE LOWER(CONCAT('%', :search, '%'))
        
       
""")
    Page<MerchantOwner> searchMerchantOwners(
            @Param("search") String search,
            Pageable pageable
    );
}
