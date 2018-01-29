package com.github.blog.membership.core;

import com.github.blog.shared.PropertiesLoader;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Properties;

/**
 * Sign in use case.
 */
public final class SignInFacade {

  private static final Properties MESSAGES = PropertiesLoader.fromResource(
      "messages.properties");
  private static final Logger LOGGER = LoggerFactory.getLogger(
      SignInFacade.class);

  private final ErrorState errorState;
  private final ValidationService validationService;
  private final UserRepository userRepository;

  /**
   * Constructor.
   */
  public SignInFacade(
      ErrorState errorState,
      ValidationService validationService,
      UserRepository userRepository) {
    this.errorState = errorState;
    this.validationService = validationService;
    this.userRepository = userRepository;
  }

  /**
   * Authenticates user request.
   */
  public SignInResponse authenticate(SignInRequest request) {
    if (!validationService.validate(request)) {
      return SignInResponse.ERROR;
    }

    AuthInfo authInfo = userRepository.findAuthInfo(
        request.getUsername().toLowerCase());
    if (authInfo == null
        || !Objects.equals(authInfo.getPassword(), request.getPassword())) {
      this.errorState.addError(MESSAGES.getProperty("signInFailed"));
      return SignInResponse.ERROR;
    }

    if (authInfo.isLocked()) {
      this.errorState.addError(MESSAGES.getProperty("userLocked"));
      return SignInResponse.ERROR;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("authenticated {}", request.getUsername());
    }

    SignInResponse response = new SignInResponse();
    response.setUsername(request.getUsername());
    return response;
  }
}