package com.example.demo.validation;


import com.example.demo.model.login.password.PasswordDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswDTOValidator implements ConstraintValidator<ValidPasswDTO, Object> {
    @Override
    public void initialize(ValidPasswDTO constraintAnnotation) {

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final PasswordDTO passwordDTO = (PasswordDTO) value;
        return passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword());
    }
}
