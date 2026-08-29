package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.TerminalType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TerminalTypeRepository
        extends JpaRepository<TerminalType, Long> {

    boolean existsByTypeCodeAndTypeName(
            String typeCode,
            String typeName
    );

    boolean existsByTypeCodeAndTypeNameAndIdNot(
            String typeCode,
            String typeName,
            Long id
    );

    Page<TerminalType>
    findByTypeCodeContainingIgnoreCaseOrTypeNameContainingIgnoreCase(
            String typeCode,
            String typeName,
            Pageable pageable
    );
}