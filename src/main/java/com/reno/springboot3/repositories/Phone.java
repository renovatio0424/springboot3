package com.reno.springboot3.repositories;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface Phone {
    String message() default "please input a phone number.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    boolean onlyNumber() default false;
}
