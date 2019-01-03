package com.github.blog.membership;

import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignUpFacade;
import com.github.blog.membership.core.UserRepository;
import com.github.blog.membership.infrastructure.mock.UserMockRepository;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;

import java.util.Optional;
import java.util.function.Function;

public final class Factory {
  private static final String MOCK_STRATEGY = "mock";

  /**
   * Constructor.
   */
  public Factory(Function<String, String> env) {
    if (!MOCK_STRATEGY.equals(Optional.ofNullable(
        env.apply("repository.strategy")).orElse(MOCK_STRATEGY))) {
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
    return new UserMockRepository();
  }
}