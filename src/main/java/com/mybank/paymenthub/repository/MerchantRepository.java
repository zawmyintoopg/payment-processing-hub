package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MerchantRepository
        extends JpaRepository<Merchant, Long> {
    boolean existsByMerchantName(String merchantName);
    boolean existsByMerchantNameAndIdNot(
            String merchantName,
            Long id
    );
    Optional<Merchant> findByMerchantNumber(String merchantNumber);
    // Search Merchant with related data
    @Query("""
        SELECT m
        FROM Merchant m
        JOIN m.merchantOwner
        JOIN m.merchantSegment
        JOIN m.merchantCategory
        WHERE
            LOWER(m.merchantNumber) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(m.merchantName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(m.merchantOwner.ownerName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(m.merchantSegment.merchantSegmentName) LIKE LOWER(CONCAT('%', :search, '%'))
            OR LOWER(m.merchantCategory.categoryName) LIKE LOWER(CONCAT('%', :search, '%'))
    """)
    Page<Merchant> searchMerchants(
            @Param("search") String search,
            Pageable pageable
    );
    // Get all merchants with relationship data
    @Query("""
        SELECT m
        FROM Merchant m
        JOIN  m.merchantOwner
        JOIN m.merchantSegment
        JOIN m.merchantCategory
    """)
    Page<Merchant> findAllWithDetails(Pageable pageable);
    // Get one merchant with relationship data
    @Query("""
        SELECT m
        FROM Merchant m
        JOIN FETCH m.merchantOwner
        JOIN FETCH m.merchantSegment
        JOIN FETCH m.merchantCategory
        WHERE m.id = :id
    """)
    Optional<Merchant> findByIdWithDetails(
            @Param("id") Long id
    );
}