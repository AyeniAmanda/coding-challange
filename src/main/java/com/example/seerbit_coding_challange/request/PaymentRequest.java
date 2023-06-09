package com.example.seerbit_coding_challange.request;

import com.example.seerbit_coding_challange.annotation.DateValidator;
import com.example.seerbit_coding_challange.utils.LocalDateTimeDeserializer;
import com.example.seerbit_coding_challange.utils.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentRequest {

    @NotNull(message = "Amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount value has to be greater than 0.0")
    @Digits(integer = Integer.MAX_VALUE, fraction = 4, message = "Ensure that the fraction is at most 4 decimal places")
    private final BigDecimal amount;

    @NotNull(message = "Date cannot be null")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateValidator
    private final LocalDateTime paymentDate;
}