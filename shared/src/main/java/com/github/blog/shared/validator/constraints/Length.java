package com.github.blog.shared.validator.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {LengthValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
         ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface Length {
  /**
   * Returns a validation message.
   */
  String message() default
      "{com.github.blog.shared.validator.constraints.Length.message}";

  /**
   * Returns a validation message for required value.
   */
  String messageRequired() default
      "{com.github.blog.shared.validator.constraints.Length.messageRequired}";

  /**
   * The length must be higher or equal to.
   */
  int min() default 0;

  /**
   * The length must be less or equal to.
   */
  int max() default Integer.MAX_VALUE;

  /**
   * Groups.
   */
  Class<?>[] groups() default {};

  /**
   * Payload.
   */
  Class<? extends Payload>[] payload() default {};
}