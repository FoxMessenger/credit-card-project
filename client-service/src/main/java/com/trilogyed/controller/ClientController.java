package com.trilogyed.controller;

import com.trilogyed.model.CreditCard;
import com.trilogyed.util.feign.CreditCardClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class ClientController {

    @Autowired
    private CreditCardClient client;

    @GetMapping(value= "/creditCard/{account}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCardBalance(@PathVariable String account) {
        return client.getCardBalance(account);
    }

    @PostMapping(value= "/creditCard/debitFunds")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard updateAccountBalance(@RequestBody CreditCard creditCard) {
        return client.updateAccountBalance(creditCard);
    }
}