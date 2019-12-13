package com.company.creditcardservice.repository;

import com.company.creditcardservice.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepo extends JpaRepository<CreditCard, Integer> {
    CreditCard findByAccountNumber(String acct);


}