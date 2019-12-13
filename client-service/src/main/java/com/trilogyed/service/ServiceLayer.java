package com.trilogyed.service;

import com.trilogyed.model.CreditCard;
import com.trilogyed.util.feign.CreditCardClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@Component
public class ServiceLayer {
    @Autowired
    CreditCardClient client;

    public ServiceLayer(CreditCardClient client) {
        this.client = client;
    }


    public BigDecimal getCardBalance(String account) {
        return client.getCardBalance(account);
    }

    public CreditCard updateBalance(CreditCard creditCard) {
        CreditCard creditCard1 = client.getCreditCardByAccountNumber(creditCard.getAccountNumber());
        creditCard1.setBalance(creditCard1.getBalance().add(creditCard.getBalance()));
        return client.updateAccountBalance(creditCard1);

    }

}
