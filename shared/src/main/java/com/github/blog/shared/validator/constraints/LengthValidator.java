package com.github.blog.shared.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public final class LengthValidator
    implements ConstraintValidator<Length, CharSequence> {

  private int min;
  private int max;
  private String message;
  private String messageRequired;

  @Override
  public void initialize(Length constraintAnnotation) {
    min = constraintAnnotation.min();

    if (min < 0) {
      throw new IllegalArgumentException(
          "The min parameter cannot be negative.");
    }

    max = constraintAnnotation.max();
    if (max < 0) {
      throw new IllegalArgumentException(
          "The max parameter cannot be negative.");
    }

    if (max < min) {
      throw new IllegalArgumentException(
          "The max parameter cannot be less than min parameter.");
    }

    message = constraintAnnotation.message();
    messageRequired = constraintAnnotation.messageRequired();
  }

  @Override
  public boolean isValid(
      CharSequence value, ConstraintValidatorContext context) {

    if (value == null || value.length() == 0) {
      context.buildConstraintViolationWithTemplate(messageRequired)
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }

    int length = value.length();
    if (length < min || length > max) {
      context.buildConstraintViolationWithTemplate(message)
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }

    return true;
  }
}