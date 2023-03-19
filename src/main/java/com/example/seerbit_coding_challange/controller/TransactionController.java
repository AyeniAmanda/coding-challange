package com.example.seerbit_coding_challange.controller;

import com.example.seerbit_coding_challange.request.PaymentRequest;
import com.example.seerbit_coding_challange.response.StatisticsResponse;
import com.example.seerbit_coding_challange.service.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/transaction")
@AllArgsConstructor
public class TransactionController {

    private static final int MAX_TRANSACTION_AGE_IN_SECONDS = 30;

    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<?> postTransaction(@RequestBody @Valid PaymentRequest paymentRequest) {
        if (isTransactionTooOld(paymentRequest.getPaymentDate()))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        transactionService.saveTransaction(paymentRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTransaction() {
        transactionService.deleteTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getTransactionStatistics() {
        return new ResponseEntity<>(transactionService.getStatistics(), HttpStatus.OK);
    }

    private boolean isTransactionTooOld(LocalDateTime timestamp) {
        return timestamp.isBefore(LocalDateTime.now().minusSeconds(MAX_TRANSACTION_AGE_IN_SECONDS));
    }
}