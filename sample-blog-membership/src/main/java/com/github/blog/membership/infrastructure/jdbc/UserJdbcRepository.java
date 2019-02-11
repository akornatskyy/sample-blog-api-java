package com.github.blog.membership.infrastructure.jdbc;

import com.github.blog.membership.core.AuthInfo;
import com.github.blog.membership.core.Registration;
import com.github.blog.membership.core.UserRepository;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import javax.sql.DataSource;

public final class UserJdbcRepository implements UserRepository {

  private static final ScalarHandler<String> STRING_SCALAR_HANDLER =
      new ScalarHandler<>();
  private static final BeanHandler<AuthInfo> AUTH_INFO_BEAN_HANDLER =
      new BeanHandler<>(AuthInfo.class);

  private final QueryRunner queryRunner;

  public UserJdbcRepository(DataSource dataSource) {
    queryRunner = new QueryRunner(dataSource);
  }

  @Override
  public AuthInfo findAuthInfo(String username) {
    try {
      return queryRunner.query(
          "SELECT id userId, password_hash passwordHash, is_locked locked "
          + "FROM users WHERE username = ?",
          AUTH_INFO_BEAN_HANDLER, username);
    } catch (SQLException ex) {
      throw new IllegalStateException(ex);
    }
  }

  @Override
  public boolean hasAccount(String username) {
    try {
      return queryRunner.query(
          "SELECT '' FROM users WHERE username = ?",
          STRING_SCALAR_HANDLER, username) != null;
    } catch (SQLException ex) {
      throw new IllegalStateException(ex);
    }
  }

  @Override
  public boolean createAccount(Registration registration) {
    try {
      return queryRunner.update(
          "INSERT INTO users (id, username, password_hash, email) "
          + "VALUES (?, ?, ?, ?)",
          registration.getUserId(),
          registration.getUsername(),
          registration.getPasswordHash(),
          registration.getEmail()) == 1;
    } catch (SQLException ex) {
      throw new IllegalStateException(ex);
    }
  }
}
