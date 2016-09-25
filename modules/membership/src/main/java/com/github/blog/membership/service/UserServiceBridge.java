package com.github.blog.membership.service;

import com.github.blog.membership.models.AuthInfo;
import com.github.blog.membership.repository.UserRepository;
import com.github.blog.shared.PropertiesLoader;
import com.github.blog.shared.service.ErrorState;

import java.util.Properties;

public final class UserServiceBridge implements UserService {

  private static final Properties messages = PropertiesLoader.fromResource(
      "membership-messages");

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
    if (authInfo == null || !authInfo.getPassword().equals(password)) {
      this.errorState.addError(messages.getProperty("signInFailed"));
      return false;
    }

    if (authInfo.isLocked()) {
      this.errorState.addError(messages.getProperty("userLocked"));
      return false;
    }

    return true;
  }
}