package com.github.blog.membership.service;

import com.github.blog.membership.models.AuthInfo;
import com.github.blog.membership.repository.UserRepository;
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