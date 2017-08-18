package com.example.demo.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = PasswValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPassw {
    String message() default "Invalid password-default from interface";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
