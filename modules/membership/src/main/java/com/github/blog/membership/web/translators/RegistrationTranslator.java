package com.github.blog.membership.web.translators;

import com.github.blog.membership.models.Registration;
import com.github.blog.membership.web.models.SignUpRequest;

public final class RegistrationTranslator {
  /**
   * Translates from {@link SignUpRequest} to {@link Registration}.
   */
  public static Registration from(SignUpRequest request) {
    Registration registration = new Registration();
    registration.setEmail(request.getEmail());
    registration.setPassword(request.getPassword());
    registration.setUsername(request.getUsername());
    return registration;
  }
}