package com.mybank.paymenthub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="created_at",updatable = false)
    private LocalDateTime createdDate;

    @Column(name="updated_at")
    private LocalDateTime updatedDate;

    private String createdBy;

    private String updatedBy;

    @PrePersist
    protected void onCreate(){
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();

    }

    @PreUpdate
    protected void onUpdate(){
        updatedDate = LocalDateTime.now();
    }



}
