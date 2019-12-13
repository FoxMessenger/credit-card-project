package com.company.creditcardservice.controller;

import com.company.creditcardservice.repository.CreditCardRepo;
import com.company.creditcardservice.model.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class CreditCardController {

    @Autowired
    private CreditCardRepo repo;

    @GetMapping(value = "/creditCards")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAllCreditCards() {
        return repo.findAll();
    }

    @GetMapping(value= "/creditCard/account/{account}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCard getCardByAccount(@PathVariable String account) {
        return repo.findByAccountNumber(account);
    }

    @GetMapping(value= "/creditCard/{account}")
    @ResponseStatus(HttpStatus.OK)
    public BigDecimal getCardBalance(@PathVariable String account) {
        return repo.findByAccountNumber(account).getbalance();
    }

    @PostMapping(value= "/creditCard/debitFunds")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard updateAccountBalance(@RequestBody @Valid CreditCard creditCard) {
        return repo.save(creditCard);
    }

    @PutMapping(value = "/creditCard")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateCreditCard(@RequestBody @Valid CreditCard creditCard ) {
        repo.save(creditCard);
    }

    @DeleteMapping(value = "/creditCard/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCreditCardById(@PathVariable int id) {
        repo.deleteById(id);
    }

//    @PostMapping(value= "/creditCard")
//    @ResponseStatus(HttpStatus.CREATED)
//    public CreditCard createNewCreditCard(@RequestBody CreditCard creditCard) {
//        return repo.save(creditCard);
//    }

}