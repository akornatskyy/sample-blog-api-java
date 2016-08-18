package com.github.blog.membership.service;

import com.github.blog.shared.service.ErrorState;

public final class UserServiceBridge implements UserService {

  private final ErrorState errorState;

  public UserServiceBridge(ErrorState errorState) {
    this.errorState = errorState;
  }

  @Override
  public boolean authenticate(String username, String password) {
    if (!"demo".equalsIgnoreCase(username)) {
      this.errorState.addError("signInFailed");
      return false;
    }

    return true;
  }
}