package com.example.seerbit_coding_challange.utils;

import com.example.seerbit_coding_challange.request.PaymentRequest;
import com.example.seerbit_coding_challange.response.StatisticsResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public final class Utils {

    private Utils() {
    }

    public static StatisticsResponse mockResponse() {
        return StatisticsResponse.builder()
                .max(new BigDecimal("10.50"))
                .min(new BigDecimal("2.33"))
                .avg(new BigDecimal("9.00"))
                .sum(new BigDecimal("27.00"))
                .count(3)
                .build();
    }

    public static PaymentRequest formRequest(String amount, LocalDateTime dateTime) {
        return PaymentRequest.builder()
                .paymentDate(dateTime)
                .amount(new BigDecimal(amount))
                .build();
    }
}

