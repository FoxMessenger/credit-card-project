package com.trilogyed.service;

import com.trilogyed.model.CreditCard;
import com.trilogyed.util.feign.CreditCardClient;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;


public class ServiceLayerTest {

    private CreditCardClient client;
    private ServiceLayer service;

    @Before
    public void setUp() throws Exception {
        setUpClientMock();
        service = new ServiceLayer(client);
    }

    private void setUpClientMock() {
        client = mock(CreditCardClient.class);

        CreditCard creditCard1 = new CreditCard("2213", new BigDecimal("100.50"));
        CreditCard creditCardAdded = new CreditCard("2213", new BigDecimal("400.00"));
        CreditCard creditCardUpdated = new CreditCard("2213", new BigDecimal("500.50"));


        doReturn(creditCard1).when(client).getCreditCardByAccountNumber("2213");
//        doReturn(new BigDecimal("100.50")).when(client).getCardBalance("2213");

        doReturn(creditCardUpdated).when(client).updateAccountBalance(creditCardUpdated);

//        doReturn(creditCardUpdated).when(client).getCreditCardByAccountNumber("2213");
    }

    @Test
    public void shouldReturnUpdatedCardBalance(){
        CreditCard creditCard1 = new CreditCard("2213", new BigDecimal("100.50"));
        CreditCard creditCardAdded = new CreditCard("2213", new BigDecimal("400.00"));
        CreditCard creditCardUpdated = new CreditCard("2213", new BigDecimal("500.50"));

        CreditCard creditCardActual = service.updateBalance(creditCardAdded);

        assertEquals(creditCardUpdated, creditCardActual);
    }
}