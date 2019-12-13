package com.company.creditcardservice.repository;

import com.company.creditcardservice.model.CreditCard;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CreditCardRepoTest {

    @Autowired
    CreditCardRepo repo;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void shouldGetCreditCardByAccountNumber(){
        CreditCard creditCard1 = new CreditCard("2213", new BigDecimal("100.50"));
        repo.save(creditCard1);

        assertEquals(creditCard1, repo.findByAccountNumber("2213"));


    }
}