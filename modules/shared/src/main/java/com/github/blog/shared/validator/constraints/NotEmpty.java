package com.github.blog.shared.validator.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NotNull
@Size(min = 1)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
         ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotEmpty {
  /**
   * Returns a validation message.
   */
  String message() default
      "{com.github.blog.shared.validator.constraints.NotEmpty.message}";

  /**
   * Groups.
   */
  Class<?>[] groups() default {};

  /**
   * Payload.
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * List of annotations.
   */
  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE,
           ElementType.CONSTRUCTOR, ElementType.PARAMETER})
  @Retention(RetentionPolicy.RUNTIME)
  @interface List {
    NotEmpty[] value();
  }
}