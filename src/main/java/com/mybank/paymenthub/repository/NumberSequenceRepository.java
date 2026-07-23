package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.NumberSequence;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NumberSequenceRepository
        extends JpaRepository<NumberSequence,String> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
            select n
            from NumberSequence n
            where n.sequenceName = :name
        """)
    NumberSequence findForUpdate(
            @Param("name") String name
    );

}