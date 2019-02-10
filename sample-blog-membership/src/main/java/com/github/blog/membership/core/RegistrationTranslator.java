package com.github.blog.membership.core;

final class RegistrationTranslator {
  private RegistrationTranslator() {
  }

  /**
   * Translates from {@link SignUpRequest} to {@link Registration}.
   */
  public static Registration from(SignUpRequest request) {
    Registration registration = new Registration();
    registration.setEmail(request.getEmail());
    registration.setUsername(request.getUsername());
    return registration;
  }
}