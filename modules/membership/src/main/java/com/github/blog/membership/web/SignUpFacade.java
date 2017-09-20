package com.github.blog.membership.web;

import com.github.blog.membership.service.UserService;
import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;
import com.github.blog.membership.web.models.SignUpRequest;
import com.github.blog.membership.web.models.SignUpResponse;
import com.github.blog.membership.web.translators.RegistrationTranslator;
import com.github.blog.shared.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SignUpFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      SignUpFacade.class);

  private final ValidationService validationService;
  private final UserService userService;

  public SignUpFacade(
      ValidationService validationService,
      UserService userService) {
    this.validationService = validationService;
    this.userService = userService;
  }

  /**
   * Creates an account.
   */
  public SignUpResponse createAccount(SignUpRequest request) {
    if (!validate(request)) {
      return SignUpResponse.ERROR;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("creating account");
    }

    if (!userService.createAccount(RegistrationTranslator.from(request))) {
      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("create account failed");
      }

      return SignUpResponse.ERROR;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("account created");
    }

    return SignUpResponse.OK;
  }

  private boolean validate(SignUpRequest request) {
    return validationService.validate(request);
  }
}