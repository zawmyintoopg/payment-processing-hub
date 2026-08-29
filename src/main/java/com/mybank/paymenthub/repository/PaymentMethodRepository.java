package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod,Long> {


}
