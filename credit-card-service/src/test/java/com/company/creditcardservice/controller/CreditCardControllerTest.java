package com.company.creditcardservice.controller;

import com.company.creditcardservice.repository.CreditCardRepo;
import com.company.creditcardservice.model.CreditCard;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CreditCardController.class)
public class CreditCardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreditCardRepo repo;
    private JacksonTester<CreditCard> jsonCreditCard;
    private JacksonTester<List<CreditCard>> jsonCreditCardList;
    private JacksonTester<BigDecimal> jsonBalance;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void shouldRetrieveAllCreditCards() throws Exception {
        CreditCard creditCard1 = new CreditCard();
        CreditCard creditCard2 = new CreditCard();
        creditCard1.setId(1);
        creditCard2.setId(2);
        List<CreditCard> creditCards = new ArrayList<CreditCard>() {{
            add(creditCard1);
            add(creditCard2);
        }};
        given(repo.findAll())
                .willReturn(creditCards);
        MockHttpServletResponse allResponse = mockMvc.perform(
                get("/creditCards")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(allResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(allResponse.getContentAsString()).isEqualTo(jsonCreditCardList.write(creditCards).getJson());
    }

    @Test
    public void shouldUpdateBalance() throws Exception {
        CreditCard creditCard1 = new CreditCard();
        CreditCard creditCardAdded = creditCard1;
        creditCardAdded.setId(1);
        given(repo.save(creditCard1))
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
    public void shouldUpdateCreditCards() throws Exception {
        CreditCard creditCard1 = new CreditCard();
        CreditCard creditCardAdded = creditCard1;
        creditCardAdded.setId(1);
        given(repo.save(creditCard1))
                .willReturn(creditCardAdded);
        CreditCard creditCard2 = new CreditCard();
        creditCard2.setId(1);
        MockHttpServletResponse updateResponse = mockMvc.perform(
                put("/creditCard")
                        .content(jsonCreditCard.write(creditCard2).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(updateResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).save(creditCard2);
    }

    @Test
    public void shouldDeleteCreditCards() throws Exception {
        CreditCard creditCard1 = new CreditCard();
        CreditCard creditCardAdded = creditCard1;
        creditCardAdded.setId(1);
        given(repo.save(creditCard1))
                .willReturn(creditCardAdded);
        MockHttpServletResponse deleteResponse = mockMvc.perform(
                delete("/creditCard/{id}", 1)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(deleteResponse.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
        verify(repo, times(1)).deleteById(creditCard1.getId());
    }

    @Test
    public void shouldGetCardBalance() throws Exception {
        CreditCard creditCard1 = new CreditCard("2213", new BigDecimal("100.50"));
        CreditCard creditCardAdded = creditCard1;
        creditCardAdded.setId(1);

        given(repo.findByAccountNumber("2213"))
                .willReturn(creditCardAdded);

        MockHttpServletResponse getResponse = mockMvc.perform(
                get("/creditCard/{account}", "2213")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(getResponse.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(getResponse.getContentAsString()).isEqualTo(jsonBalance.write(new BigDecimal("100.50")).getJson());
    }


    @Test
    public void shouldThrowError_When() throws Exception {
        CreditCard creditCard1 = new CreditCard(" ", null);
        CreditCard creditCardAdded = creditCard1;
        creditCardAdded.setId(1);
        given(repo.save(creditCard1))
                .willReturn(creditCardAdded);
        MockHttpServletResponse createdResponse = mockMvc.perform(
                post("/creditCard/debitFunds")
                        .content(jsonCreditCard.write(creditCard1).getJson())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();
        assertThat(createdResponse.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
    }
}