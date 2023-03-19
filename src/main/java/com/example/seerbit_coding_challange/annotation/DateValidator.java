package com.example.seerbit_coding_challange.annotation;

import com.example.seerbit_coding_challange.annotation.validator.DateValidatorImpl;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;

@Target({FIELD, PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = DateValidatorImpl.class)
public @interface DateValidator {
    Class<?>[] groups() default {};

    String message() default "Date cannot be in the future";

    Class<? extends Payload>[] payload() default {};
}