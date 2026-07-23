package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantCategory;
import com.mybank.paymenthub.enums.MerchantCategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MerchantCategoryRepository extends JpaRepository<MerchantCategory,Long> {

    Optional<MerchantCategory> findByCategoryCode(String categoryCode);
    Optional<MerchantCategory> findByCategoryName(String categoryName);

    // FOR CREATE CATEGORY
    boolean existsByCategoryCode(String categoryCode);
    boolean existsByCategoryName(String categoryName);

    // FOR UPDATE CATEGORY
    boolean existsByCategoryCodeAndIdNot(String categoryCode,Long id);
    boolean existsByCategoryNameAndIdNot(String categoryName,Long id);
    boolean existsByMerchantCategoryStatusAndIdNot(
            MerchantCategoryStatus status,
            Long id);

}
