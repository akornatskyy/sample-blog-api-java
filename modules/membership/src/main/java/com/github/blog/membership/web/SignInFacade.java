package com.github.blog.membership.web;

import com.github.blog.membership.service.UserService;
import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;

public final class SignInFacade {

  private final UserService userService;

  public SignInFacade(UserService userService) {
    this.userService = userService;
  }

  /**
   * Authenticates user request.
   */
  public SignInResponse authenticate(SignInRequest request) {
    if (!this.userService.authenticate(
        request.getUsername(),
        request.getPassword())) {
      return null;
    }

    SignInResponse response = new SignInResponse();
    response.setUsername(request.getUsername());
    return response;
  }
}