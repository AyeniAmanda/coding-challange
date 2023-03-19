package com.example.seerbit_coding_challange.service;

import com.example.seerbit_coding_challange.request.PaymentRequest;
import com.example.seerbit_coding_challange.response.StatisticsResponse;

import java.util.List;

public interface TransactionService {

    void saveTransaction(PaymentRequest paymentRequest);
    void deleteTransactions();
    StatisticsResponse getStatistics();
    List<PaymentRequest> fetchAllTransactionList();
}