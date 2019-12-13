package com.trilogyed.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trilogyed.model.CreditCard;
import com.trilogyed.util.feign.CreditCardClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ClientController.class)
public class CreditCardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CreditCardClient client;
    private JacksonTester<CreditCard> jsonCreditCard;
    private JacksonTester<BigDecimal> jsonBalance;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldUpdateBalance() throws Exception {
        CreditCard creditCard1 = new CreditCard();
        CreditCard creditCardAdded = creditCard1;
//        creditCardAdded.setId(1);
        given(client.updateAccountBalance(creditCard1))
                .willReturn(creditCardAdded);
        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/creditCard/debitFunds")
                        .content(jsonCreditCard.write(creditCard1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(createdResponse.getContentAsString()).isEqualTo(jsonCreditCard.write(creditCardAdded).getJson());
    }


    @Test
    public void shouldGetCardBalance() throws Exception {
        CreditCard creditCard1 = new CreditCard("2213", new BigDecimal("100.50"));
        CreditCard creditCardAdded = creditCard1;
//        creditCardAdded.setId(1);

        given(client.getCardBalance("2213"))
                .willReturn(new BigDecimal("100.50"));

        MockHttpServletResponse getResponse = mockMvc.perform(
                get("/creditCard/{account}", "2213")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

//        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.getContentAsString()).isEqualTo(jsonBalance.write(new BigDecimal("100.50")).getJson());
    }

}