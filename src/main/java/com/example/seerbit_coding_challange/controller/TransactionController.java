package com.example.seerbit_coding_challange.controller;

import com.example.seerbit_coding_challange.request.TransactionRequest;
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

    private final TransactionService transactionService;

    @PostMapping()
    public ResponseEntity<?> postTransaction(@RequestBody @Valid TransactionRequest transactionRequest) {
        if (transactionRequest.getTimestamp().isBefore(LocalDateTime.now().minusSeconds(30)))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        //Only save transactions completed less than 30seconds
        transactionService.saveTransaction(transactionRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> getTransactionStatistics() {
        return new ResponseEntity<>(transactionService.getStatistics(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTransaction() {
        transactionService.deleteTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
