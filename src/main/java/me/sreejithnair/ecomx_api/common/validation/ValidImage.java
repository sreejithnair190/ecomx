package me.sreejithnair.ecomx_api.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ImageValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidImage {

    String message() default "Invalid image file";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    long maxSizeInMB() default 5;

    String[] allowedTypes() default {"image/jpeg", "image/png", "image/gif", "image/webp"};

    boolean required() default false;
}
