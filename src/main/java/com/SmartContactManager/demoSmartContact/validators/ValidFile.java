package com.SmartContactManager.demoSmartContact.validators;
import java.lang.annotation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface ValidFile {

    String message() default "Invalid file";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {}; 


}
