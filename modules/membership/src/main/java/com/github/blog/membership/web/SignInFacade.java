package com.github.blog.membership.web;

import com.github.blog.membership.service.UserService;
import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;
import com.github.blog.shared.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;

public final class SignInFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      SignInFacade.class);

  private final ValidationService validationService;
  private final UserService userService;

  public SignInFacade(
      ValidationService validationService,
      UserService userService) {
    this.validationService = validationService;
    this.userService = userService;
  }

  /**
   * Authenticates user request.
   */
  public SignInResponse authenticate(SignInRequest request) {
    if (!validate(request)) {
      return SignInResponse.ERROR;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("authenticating {}", request.getUsername());
    }

    if (!this.userService.authenticate(
        request.getUsername(),
        request.getPassword())) {

      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("authentication failed for {}", request.getUsername());
      }

      return SignInResponse.ERROR;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("authenticated {}", request.getUsername());
    }

    SignInResponse response = new SignInResponse();
    response.setUsername(request.getUsername());
    return response;
  }

  private boolean validate(SignInRequest request) {
    return validationService.validate(request);
  }
}