package com.nkonzo.student_portal_api.common.validation.passport;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Constraint(validatedBy = PassportNumberOrIDValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassportNumberOrID {
    String message() default "Passport number or ID number must be provided!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
