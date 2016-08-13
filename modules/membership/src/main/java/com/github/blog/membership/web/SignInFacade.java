package com.github.blog.membership.web;

import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;

public final class SignInFacade {
  /**
   * Authenticates user request.
   */
  public SignInResponse authenticate(SignInRequest request) {
    SignInResponse response = new SignInResponse();
    response.setUsername(request.getUsername());
    return response;
  }
}