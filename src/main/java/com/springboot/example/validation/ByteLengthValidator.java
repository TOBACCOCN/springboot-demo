package com.springboot.example.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;

public class ByteLengthValidator implements ConstraintValidator<ByteLength, String> {

    private int min;
    private int max;

    @Override
    public void initialize(ByteLength constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (min == 0) {
            return true;
        }

        if (s == null || s.length() == 0) {
            return false;
        }

        int length = s.getBytes(StandardCharsets.UTF_8).length;
        return length >= min && length <= max;
    }
}
