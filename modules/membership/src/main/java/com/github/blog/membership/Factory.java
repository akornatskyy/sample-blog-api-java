package com.github.blog.membership;

import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignUpFacade;
import com.github.blog.membership.core.UserRepository;
import com.github.blog.membership.infrastructure.mock.MockUserRepository;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;

import java.util.Properties;

public final class Factory {
  public Factory(Properties properties) {
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