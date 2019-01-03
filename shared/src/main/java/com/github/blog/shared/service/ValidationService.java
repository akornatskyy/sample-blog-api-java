package com.github.blog.shared.service;

import javax.validation.Validation;
import javax.validation.ValidatorFactory;

public final class ValidationService {
  private static final ValidatorFactory VALIDATOR_FACTORY =
      Validation.buildDefaultValidatorFactory();

  private final ErrorState errorState;

  public ValidationService(ErrorState errorState) {
    this.errorState = errorState;
  }

  /**
   * Validates model based on discovered annotations.
   */
  public <T> boolean validate(T model) {
    VALIDATOR_FACTORY.getValidator().validate(model).forEach(
        violation -> errorState.addError(
            violation.getPropertyPath().toString(), violation.getMessage()));
    return !errorState.hasErrors();
  }
}
