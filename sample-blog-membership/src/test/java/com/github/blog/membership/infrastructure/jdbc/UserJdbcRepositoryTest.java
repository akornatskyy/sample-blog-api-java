package com.github.blog.membership.infrastructure.jdbc;

import com.github.blog.membership.core.AuthInfo;
import com.github.blog.membership.core.Registration;
import com.github.blog.membership.core.UserRepository;
import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;
import javax.sql.DataSource;

public final class UserJdbcRepositoryTest {

  private final DataSource dataSource;
  private final ScriptRunner scriptRunner;
  private final UserRepository repository;

  public UserJdbcRepositoryTest() throws IOException {
    dataSource = mysql();
    scriptRunner = ScriptRunner.from(dataSource)
        .withRoot(Paths.get("../"))
        .add(Paths.get("schema.sql"))
        .add(Paths.get("samples.sql"));
    repository = new UserJdbcRepository(dataSource);
  }

  @BeforeMethod
  public void setUp() throws SQLException {
    scriptRunner.execute();
  }

  @Test
  public void testFindAuthInfo() {
    AuthInfo info = repository.findAuthInfo("demo");

    Assert.assertNotNull(info);
    Assert.assertEquals(
        "854b5ac3-5e68-4a58-8900-28ea5082f718", info.getUserId());
  }

  @Test
  public void testHasAccount() {
    Assert.assertTrue(repository.hasAccount("demo"));
    Assert.assertFalse(repository.hasAccount("x"));
  }

  @Test
  public void testCreateAccount() {
    Registration r = new Registration();
    r.setUserId(UUID.randomUUID().toString());
    r.setUsername(r.getUserId().substring(9, 29));
    r.setPasswordHash("password");
    r.setEmail("test@somewhere.com");

    Assert.assertTrue(repository.createAccount(r));
    Assert.assertTrue(repository.hasAccount(r.getUsername()));
  }

  private static DataSource mysql() {
    MysqlDataSource dataSource = new MysqlConnectionPoolDataSource();
    dataSource.setUrl(
        "jdbc:mysql://root@localhost/sample_blog"
        + "?logger=com.mysql.cj.log.Slf4JLogger&profileSQL=true");
    return dataSource;
  }
}