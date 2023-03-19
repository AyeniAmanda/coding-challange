package com.example.seerbit_coding_challange.service;


import com.example.seerbit_coding_challange.request.PaymentRequest;
import com.example.seerbit_coding_challange.response.StatisticsResponse;
import com.example.seerbit_coding_challange.serviceImpl.TransactionServiceImpl;
import com.example.seerbit_coding_challange.utils.Utils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceImplTest {

    private static final BigDecimal AMOUNT_20_50 = new BigDecimal("20.50");
    private static final BigDecimal AMOUNT_30_50 = new BigDecimal("30.50");
    private static final BigDecimal AMOUNT_50_50 = new BigDecimal("50.50");

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        this.transactionService = new TransactionServiceImpl();
    }

    @AfterEach
    void tearDown() {
        this.transactionService.deleteTransactions();
    }

    @Test
    @DisplayName("Should increment list size on post transaction")
    void shouldIncrementListSizeOnPostTransaction() {
        PaymentRequest paymentRequest = Utils.formRequest(AMOUNT_30_50.toString(), LocalDateTime.now().minusSeconds(27));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(1, transactionService.fetchAllTransactionList().size(), "Transaction list size should be 1");

        paymentRequest = Utils.formRequest(AMOUNT_50_50.toString(), LocalDateTime.now().minusSeconds(15));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(2, transactionService.fetchAllTransactionList().size(), "Transaction list size should be 2");
    }

    @Test
    @DisplayName("Should return statistics data on get statistics")
    void shouldReturnDataOnGetStatistics() throws InterruptedException {
        populateTransactionDataAndAssert();
        // Wait for 7 seconds, this will make some transactions in the requestList stale
        TimeUnit.SECONDS.sleep(7);

        StatisticsResponse response = transactionService.getStatistics();
        assertEquals(4, response.getCount(), "Transaction count should be 4");
        assertEquals(AMOUNT_20_50, response.getMin(), "Minimum transaction amount should be 20.50");
        assertEquals(AMOUNT_50_50, response.getMax(), "Maximum transaction amount should be 50.50");
        assertEquals(new BigDecimal("142.00"), response.getSum(), "Transaction sum should be 142");
        assertEquals(new BigDecimal("35.50"), response.getAvg(), "Transaction average should be 35.50");
    }

    @Test
    @DisplayName("Should delete all data on action")
    void shouldDeleteAllDataOnAction() {
        populateTransactionDataAndAssert();
        this.transactionService.deleteTransactions();
        assertEquals(0, transactionService.fetchAllTransactionList().size(), "Transaction list should be empty");
    }

    private void populateTransactionDataAndAssert() {
        PaymentRequest paymentRequest = Utils.formRequest(AMOUNT_30_50.toString(), LocalDateTime.now().minusSeconds(27));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(1, transactionService.fetchAllTransactionList().size(),
                "Size of the Transaction List Should be 1");

        //Add again
        paymentRequest = Utils.formRequest(AMOUNT_50_50.toString(), LocalDateTime.now().minusSeconds(15));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(2, transactionService.fetchAllTransactionList().size(),
                "Size of the Transaction List Should increment by 1");

        paymentRequest = Utils.formRequest(AMOUNT_20_50.toString(), LocalDateTime.now().minusSeconds(25));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(3, transactionService.fetchAllTransactionList().size(),
                "Size of the Transaction List Should increment by 1");

        paymentRequest = Utils.formRequest(AMOUNT_50_50.toString(), LocalDateTime.now().minusSeconds(25));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(4, transactionService.fetchAllTransactionList().size(),
                "Size of the Transaction List Should increment by 1");

        paymentRequest = Utils.formRequest(AMOUNT_20_50.toString(), LocalDateTime.now().minusSeconds(5));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(5, transactionService.fetchAllTransactionList().size(),
                "Size of the Transaction List Should increment by 1");

        paymentRequest = Utils.formRequest(AMOUNT_50_50.toString(), LocalDateTime.now().minusSeconds(9));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(6, transactionService.fetchAllTransactionList().size(),
                "Size of the Transaction List Should increment by 1");

        //Add again
        paymentRequest = Utils.formRequest(AMOUNT_20_50.toString(), LocalDateTime.now().minusSeconds(10));
        transactionService.saveTransaction(paymentRequest);
        assertEquals(7, transactionService.fetchAllTransactionList().size(),
                "Size of the Transaction List Should increment by 1");
    }
}