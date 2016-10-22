package com.github.blog.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.blog.membership.repository.UserRepository;
import com.github.blog.membership.repository.mock.UserRepositoryImpl;
import com.github.blog.membership.service.UserService;
import com.github.blog.membership.service.UserServiceBridge;
import com.github.blog.membership.web.SignInFacade;
import com.github.blog.shared.service.ErrorState;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class Config extends WebMvcConfigurerAdapter {

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

  @Override
  public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
    converters.removeIf(
        c -> c.getClass().equals(MappingJackson2HttpMessageConverter.class));
    ObjectMapper mapper = new ObjectMapper();
    mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    converters.add(0, new MappingJackson2HttpMessageConverter(mapper));
  }
}