package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.User;
import org.hibernate.dialect.unique.CreateTableUniqueDelegate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);
}
