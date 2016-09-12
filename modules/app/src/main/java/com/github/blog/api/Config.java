package com.github.blog.api;

import com.github.blog.membership.repository.UserRepository;
import com.github.blog.membership.repository.mock.UserRepositoryImpl;
import com.github.blog.membership.service.UserService;
import com.github.blog.membership.service.UserServiceBridge;
import com.github.blog.membership.web.SignInFacade;
import com.github.blog.shared.service.ErrorState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {

  @Bean
  @Scope("request")
  public ErrorState errorState() {
    return new ErrorState();
  }

  @Bean
  @Scope("prototype")
  public UserRepository userRepository() {
    return new UserRepositoryImpl();
  }

  @Bean
  @Scope("prototype")
  public UserService userService(ErrorState errorState,
                                 UserRepository userRepository) {
    return new UserServiceBridge(errorState, userRepository);
  }

  @Bean
  @Scope("prototype")
  public SignInFacade signInFacade(UserService userService) {
    return new SignInFacade(userService);
  }
}