package com.example.demo.validation;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswValidator implements ConstraintValidator<ValidPassw, String> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void initialize(ValidPassw constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return password.length() > 1;
    }
}
