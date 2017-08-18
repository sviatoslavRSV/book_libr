package com.example.demo.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Constraint(validatedBy = PasswDTOValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPasswDTO {
    String message() default "Passwords don't match - from interface";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
