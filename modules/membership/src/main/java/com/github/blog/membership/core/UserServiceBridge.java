package com.github.blog.membership.core;

import com.github.blog.shared.PropertiesLoader;
import com.github.blog.shared.service.ErrorState;

import java.util.Objects;
import java.util.Properties;

public final class UserServiceBridge implements UserService {

  private static final Properties messages = PropertiesLoader.fromResource(
      "user-service.properties");

  private final ErrorState errorState;
  private final UserRepository userRepository;

  public UserServiceBridge(ErrorState errorState,
                           UserRepository userRepository) {
    this.errorState = errorState;
    this.userRepository = userRepository;
  }

  @Override
  public boolean authenticate(String username, String password) {
    AuthInfo authInfo = userRepository.findAuthInfo(username.toLowerCase());
    if (authInfo == null || !Objects.equals(authInfo.getPassword(), password)) {
      this.errorState.addError(messages.getProperty("signInFailed"));
      return false;
    }

    if (authInfo.isLocked()) {
      this.errorState.addError(messages.getProperty("userLocked"));
      return false;
    }

    return true;
  }

  @Override
  public boolean createAccount(Registration registration) {
    if (userRepository.hasAccount(registration.getUsername())) {
      this.errorState.addError(
          "username",
          messages.getProperty("alreadyRegistered"));
      return false;
    }

    if (!userRepository.createAccount(registration)) {
      this.errorState.addError(messages.getProperty("unableCreateAccount"));
      return false;
    }

    return true;
  }
}