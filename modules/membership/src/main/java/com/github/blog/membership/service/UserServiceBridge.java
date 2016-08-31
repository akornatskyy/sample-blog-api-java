package com.github.blog.membership.service;

import com.github.blog.shared.service.ErrorState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class UserServiceBridge implements UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      UserServiceBridge.class);
  private static final Properties messages = new Properties();

  static {
    InputStream is = UserServiceBridge.class.getClassLoader()
        .getResourceAsStream("membership-messages.properties");
    if (is != null) {
      try {
        messages.load(is);
      } catch (IOException e) {
        LOGGER.warn("Failed to load messages.", e);
      } finally {
        try {
          is.close();
        } catch (IOException e) {
          LOGGER.warn("Failed to close messages input stream.", e);
        }
      }
    }
  }

  private final ErrorState errorState;

  public UserServiceBridge(ErrorState errorState) {
    this.errorState = errorState;
  }

  @Override
  public boolean authenticate(String username, String password) {
    if (!"demo".equalsIgnoreCase(username)) {
      this.errorState.addError(messages.getProperty("signInFailed"));
      return false;
    }

    return true;
  }
}