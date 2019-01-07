package com.github.blog.shared.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@SuppressWarnings({"PMD.CyclomaticComplexity", "PMD.NPathComplexity"})
public final class LengthValidator
    implements ConstraintValidator<Length, CharSequence> {

  private Length length;

  @Override
  public void initialize(Length length) {
    int min = length.min();

    if (min < 0) {
      throw new IllegalArgumentException(
          "The min parameter cannot be negative.");
    }

    int max = length.max();
    if (max < 0) {
      throw new IllegalArgumentException(
          "The max parameter cannot be negative.");
    }

    if (max < min) {
      throw new IllegalArgumentException(
          "The max parameter cannot be less than min parameter.");
    }

    this.length = length;
  }

  @Override
  public boolean isValid(
      CharSequence value, ConstraintValidatorContext context) {

    if (length.min() > 0) {
      if (value == null || value.length() == 0) {
        context.buildConstraintViolationWithTemplate(length.messageRequired())
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
      }

      if (length.min() == length.max() && value.length() != length.min()) {
        context.buildConstraintViolationWithTemplate(length.messageExact())
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
      }

      if (length.max() == Integer.MAX_VALUE && value.length() < length.min()) {
        context.buildConstraintViolationWithTemplate(length.messageMinLength())
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
      }

      if (value.length() < length.min() || value.length() > length.max()) {
        context.buildConstraintViolationWithTemplate(length.message())
            .addConstraintViolation()
            .disableDefaultConstraintViolation();
        return false;
      }
    }

    if (value != null && value.length() > length.max()) {
      context.buildConstraintViolationWithTemplate(length.messageMaxLength())
          .addConstraintViolation()
          .disableDefaultConstraintViolation();
      return false;
    }

    return true;
  }
}