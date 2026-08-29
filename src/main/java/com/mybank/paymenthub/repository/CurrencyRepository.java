package com.mybank.paymenthub.repository;

import com.mybank.paymenthub.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRepository extends JpaRepository<Currency,Long> {

}
