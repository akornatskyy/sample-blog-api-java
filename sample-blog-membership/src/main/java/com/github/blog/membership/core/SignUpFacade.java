package com.github.blog.membership.core;

import com.github.blog.shared.PropertiesLoader;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Sign up use case.
 */
public final class SignUpFacade {

  private static final Properties MESSAGES = PropertiesLoader.fromResource(
      "messages.properties");
  private static final Logger LOGGER = LoggerFactory.getLogger(
      SignUpFacade.class);

  private final ErrorState errorState;
  private final ValidationService validationService;
  private final UserRepository userRepository;

  /**
   * Constructor.
   */
  public SignUpFacade(
      ErrorState errorState,
      ValidationService validationService,
      UserRepository userRepository) {
    this.errorState = errorState;
    this.validationService = validationService;
    this.userRepository = userRepository;
  }

  /**
   * Creates an account.
   */
  public SignUpResponse createAccount(SignUpRequest request) {
    if (!validationService.validate(request)) {
      return SignUpResponse.ERROR;
    }

    if (userRepository.hasAccount(request.getUsername())) {
      this.errorState.addError(
          "username",
          MESSAGES.getProperty("alreadyRegistered"));
      return SignUpResponse.ERROR;
    }

    Registration registration = RegistrationTranslator.from(request);
    registration.setPasswordHash(
        PasswordHashHelper.generateFromPassword(request.getPassword()));
    if (!userRepository.createAccount(registration)) {
      this.errorState.addError(MESSAGES.getProperty("unableCreateAccount"));
      return SignUpResponse.ERROR;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("account created");
    }

    return SignUpResponse.OK;
  }
}