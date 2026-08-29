package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.MerchantCategory;
import org.springframework.data.jpa.repository.JpaRepository;
public interface MerchantCategoryRepository
        extends JpaRepository<MerchantCategory,Long> {

    // FOR CREATE CATEGORY
    boolean existsByCategoryCode(
            String categoryCode
    );
    boolean existsByCategoryName(
            String categoryName
    );
    // FOR UPDATE CATEGORY
    boolean existsByCategoryCodeAndId(
            String categoryCode, Long id
    );
    boolean existsByCategoryNameAndId(
            String categoryName,
            Long id
    );

}
