package com.github.blog.membership.web;

import com.github.blog.membership.service.UserService;
import com.github.blog.membership.web.models.SignUpRequest;
import com.github.blog.membership.web.models.SignUpResponse;
import com.github.blog.membership.web.translators.RegistrationTranslator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SignUpFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      SignUpFacade.class);

  private final UserService userService;

  public SignUpFacade(UserService userService) {
    this.userService = userService;
  }

  /**
   * Creates an account.
   */
  public SignUpResponse createAccount(SignUpRequest request) {

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("creating account");
    }

    if (!userService.createAccount(RegistrationTranslator.from(request))) {
      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("create account failed");
      }

      return null;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("account created");
    }

    return SignUpResponse.OK;
  }
}