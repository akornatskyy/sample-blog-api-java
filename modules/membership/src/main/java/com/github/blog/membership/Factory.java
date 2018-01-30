package com.github.blog.membership;

import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignUpFacade;
import com.github.blog.membership.core.UserRepository;
import com.github.blog.membership.infrastructure.mock.MockUserRepository;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;

import java.util.Properties;

public final class Factory {
  private static final String MOCK_STRATEGY = "mock";

  /**
   * Constructor.
   */
  public Factory(Properties properties) {
    if (!MOCK_STRATEGY.equals(properties.getProperty(
        "repository.strategy", MOCK_STRATEGY))) {
      throw new IllegalStateException("Unknown repository strategy.");
    }
  }

  /**
   * Create SignInFacade.
   */
  public SignInFacade createSignInFacade(ErrorState errorState) {
    return new SignInFacade(
        errorState,
        validationService(errorState),
        userRepository());
  }

  /**
   * Create SignUpFacade.
   */
  public SignUpFacade createSignUpFacade(ErrorState errorState) {
    return new SignUpFacade(
        errorState,
        validationService(errorState),
        userRepository());
  }

  private ValidationService validationService(ErrorState errorState) {
    return new ValidationService(errorState);
  }

  private UserRepository userRepository() {
    return new MockUserRepository();
  }
}