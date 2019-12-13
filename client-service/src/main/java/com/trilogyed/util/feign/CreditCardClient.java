package com.trilogyed.util.feign;

import com.trilogyed.model.CreditCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "credit-card-service")
public interface CreditCardClient {

    @GetMapping(value= "/creditCard/{account}")
    BigDecimal getCardBalance(@PathVariable String account);

    @GetMapping(value= "/creditCard/account/{account}")
    CreditCard getCreditCardByAccountNumber(@PathVariable String account);

    @PostMapping(value= "/creditCard/debitFunds")
    CreditCard updateAccountBalance(@RequestBody CreditCard creditCard);
}
