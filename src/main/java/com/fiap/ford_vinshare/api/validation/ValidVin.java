package com.fiap.ford_vinshare.api.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = VinValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVin {
    String message() default "VIN deve ter 17 caracteres alfanumericos e nao pode conter I, O ou Q";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
