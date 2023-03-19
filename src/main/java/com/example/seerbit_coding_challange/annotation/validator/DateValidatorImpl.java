package com.example.seerbit_coding_challange.annotation.validator;

import com.example.seerbit_coding_challange.annotation.DateValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class DateValidatorImpl implements ConstraintValidator<DateValidator, LocalDateTime> {

    public boolean isValid(LocalDateTime date, ConstraintValidatorContext context) {
        return Objects.nonNull(date) && !date.toLocalDate().isAfter(LocalDate.now());
    }
}