package com.github.blog.membership.web;

import com.github.blog.membership.service.UserService;
import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SignInFacade {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      SignInFacade.class);

  private final UserService userService;

  public SignInFacade(UserService userService) {
    this.userService = userService;
  }

  /**
   * Authenticates user request.
   */
  public SignInResponse authenticate(SignInRequest request) {

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("authenticating {}", request.getUsername());
    }

    if (!this.userService.authenticate(
        request.getUsername(),
        request.getPassword())) {

      if (LOGGER.isWarnEnabled()) {
        LOGGER.warn("authentication failed for {}", request.getUsername());
      }

      return null;
    }

    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("authenticated {}", request.getUsername());
    }

    SignInResponse response = new SignInResponse();
    response.setUsername(request.getUsername());
    return response;
  }
}