package com.example.seerbit_coding_challange.service;

import com.example.seerbit_coding_challange.request.TransactionRequest;
import com.example.seerbit_coding_challange.response.StatisticsResponse;

import java.util.List;

public interface TransactionService {

    void saveTransaction(TransactionRequest transactionRequest);

    StatisticsResponse getStatistics();

    List<TransactionRequest> fetchAllTransactionList();

    void deleteTransactions();
}