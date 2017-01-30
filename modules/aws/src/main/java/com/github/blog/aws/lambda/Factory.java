package com.github.blog.aws.lambda;

import com.github.blog.membership.repository.UserRepository;
import com.github.blog.membership.repository.mock.UserRepositoryImpl;
import com.github.blog.membership.service.UserService;
import com.github.blog.membership.service.UserServiceBridge;
import com.github.blog.membership.web.SignInFacade;
import com.github.blog.membership.web.SignUpFacade;
import com.github.blog.shared.service.ErrorState;

import java.util.Properties;

class Factory {
  public Factory(Properties properties) {
  }

  public WelcomeFacade createWelcomeFacade() {
    return new WelcomeFacade();
  }

  public SignInFacade createSignInFacade(ErrorState errorState) {
    return new SignInFacade(userService(errorState));
  }

  public SignUpFacade createSignUpFacade(ErrorState errorState) {
    return new SignUpFacade(userService(errorState));
  }

  private UserService userService(ErrorState errorState) {
    return new UserServiceBridge(errorState, userRepository());
  }

  private UserRepository userRepository() {
    return new UserRepositoryImpl();
  }
}