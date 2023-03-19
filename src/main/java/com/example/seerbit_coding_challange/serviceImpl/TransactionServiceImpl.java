package com.example.seerbit_coding_challange.serviceImpl;

import com.example.seerbit_coding_challange.request.PaymentRequest;
import com.example.seerbit_coding_challange.response.StatisticsResponse;
import com.example.seerbit_coding_challange.service.TransactionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final List<PaymentRequest> paymentRequestList = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void saveTransaction(PaymentRequest paymentRequest) {
        //Add request to in-memory List
        paymentRequestList.add(paymentRequest);
    }

    @Override
    public void deleteTransactions() {
        this.paymentRequestList.clear();
    }

    @Override
    public StatisticsResponse getStatistics() {
        if (paymentRequestList.isEmpty())
            return new StatisticsResponse();

        BigDecimal max = paymentRequestList.get(0).getAmount();
        if (max == null) {
            max = new BigDecimal("0.00");
        }
        BigDecimal min = max;
        BigDecimal sum = new BigDecimal("0.00");
        long count = 0;

        synchronized (paymentRequestList) {
            // Must be in synchronized block
            for (PaymentRequest tran : paymentRequestList) {
                if (tran.getPaymentDate().isBefore(LocalDateTime.now().minusSeconds(30)))
                    continue;

                if (tran.getAmount().compareTo(max) > 0)
                    max = tran.getAmount();


                if (tran.getAmount().compareTo(min) < 0)
                    min = tran.getAmount();

                sum = sum.add(tran.getAmount());
                ++count;
            }

            max = max.setScale(2, RoundingMode.HALF_UP);
            min = min.setScale(2, RoundingMode.HALF_UP);
            sum = sum.setScale(2, RoundingMode.HALF_UP);

            BigDecimal avg = count != 0 ? sum.divide(BigDecimal.valueOf(count), RoundingMode.HALF_UP) : new BigDecimal("0.00");
            return StatisticsResponse.builder()
                    .max(max).min(min).avg(avg)
                    .sum(sum).count(count).build();
        }
    }

    @Override
    public List<PaymentRequest> fetchAllTransactionList() {
        return this.paymentRequestList;
    }
}