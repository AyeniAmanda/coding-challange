package com.example.seerbit_coding_challange.controller;

import com.example.seerbit_coding_challange.request.PaymentRequest;
import com.example.seerbit_coding_challange.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.example.seerbit_coding_challange.utils.Utils.mockResponse;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Transaction Controller Test")
class TransactionControllerTest {

    private final String URL_BASE = "/transaction";
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        objectMapper = new ObjectMapper();
        doNothing().when(transactionService).saveTransaction(any());
        when(transactionService.getStatistics()).thenReturn(mockResponse());
        doNothing().when(transactionService).deleteTransactions();
    }

    @Test
    @DisplayName("Should successfully post transaction")
    void shouldSuccessfullyPostTransaction() throws Exception {
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .amount(new BigDecimal("12.3343"))
                .paymentDate(LocalDateTime.now())
                .build();

        postTransaction(paymentRequest, status().isCreated());
    }

    @Test
    @DisplayName("Given payment timestamp is stale by at least 30 seconds, then should return status code 204 No Content")
    void whenTimestampIsStaleByAtLeast30SecondsThenShouldReturnStatusNoContent() throws Exception {
        PaymentRequest transactionRequest = PaymentRequest.builder()
                .amount(new BigDecimal("12.3343"))
                .paymentDate(LocalDateTime.parse("2022-05-13T01:30:51.312Z", ISO_DATE_TIME))
                .build();

        postTransaction(transactionRequest, status().isNoContent());
    }

    @Test
    @DisplayName("When statistics are fetched, then should return status code 200 OK with correct JSON content")
    void whenStatisticsAreFetchedShouldReturnSuccess() throws Exception {
        mockMvc.perform(get(URL_BASE + "/statistics")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.max", Is.is(10.50)))
                .andExpect(jsonPath("$.min", Is.is(2.33)))
                .andExpect(jsonPath("$.avg", Is.is(9.00)))
                .andExpect(jsonPath("$.sum", Is.is(27.00)))
                .andExpect(jsonPath("$.count", Is.is(3)))
                .andDo(print());
    }

    @Test
    @DisplayName("When delete endpoint is invoked, then should return status code 204 No Content")
    void whenDeleteEndpointIsInvokedShouldReturnNoContentStatus() throws Exception {
        mockMvc.perform(delete(URL_BASE))
                .andExpect(status().isNoContent())
                .andDo(print());
    }

    private void postTransaction(PaymentRequest paymentRequest, ResultMatcher expectedStatus) throws Exception {
        mockMvc.perform(post(URL_BASE)
                        .contentType(APPLICATION_JSON).content(asJsonString(paymentRequest)))
                .andExpect(expectedStatus)
                .andDo(print());
    }

    private String asJsonString(final Object obj) {
        try {
            return this.objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}