package com.example.seerbit_coding_challange.annotation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class DateValidatorImpl implements ConstraintValidator<com.example.codingchallenge.validator.DateValidator, LocalDateTime> {

    public boolean isValid(LocalDateTime date, ConstraintValidatorContext cxt) {
        if(date == null) return false;
        return !date.isAfter(LocalDateTime.now());
    }
}
