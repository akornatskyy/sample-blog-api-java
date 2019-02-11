package com.github.blog.membership;

import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignUpFacade;
import com.github.blog.membership.core.UserRepository;
import com.github.blog.membership.infrastructure.jdbc.UserJdbcRepository;
import com.github.blog.membership.infrastructure.mock.UserMockRepository;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Function;

public final class Factory {

  private static final Logger LOGGER = LoggerFactory.getLogger(
      Factory.class);

  private final UserRepository userRepository;

  /**
   * Constructor.
   */
  public Factory(Function<String, String> env) {
    String strategy = env.apply("repository.strategy");
    if (LOGGER.isInfoEnabled()) {
      LOGGER.info("using {} repository strategy", strategy);
    }

    switch (strategy) {
      case "mock":
        userRepository = new UserMockRepository();
        break;
      case "jdbc":
        MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
        dataSource.setUrl(env.apply("repository.url"));
        userRepository = new UserJdbcRepository(dataSource);
        break;
      default:
        throw new IllegalStateException("Unknown repository strategy.");
    }
  }

  /**
   * Create SignInFacade.
   */
  public SignInFacade createSignInFacade(ErrorState errorState) {
    return new SignInFacade(
        errorState,
        validationService(errorState),
        userRepository);
  }

  /**
   * Create SignUpFacade.
   */
  public SignUpFacade createSignUpFacade(ErrorState errorState) {
    return new SignUpFacade(
        errorState,
        validationService(errorState),
        userRepository);
  }

  private ValidationService validationService(ErrorState errorState) {
    return new ValidationService(errorState);
  }
}