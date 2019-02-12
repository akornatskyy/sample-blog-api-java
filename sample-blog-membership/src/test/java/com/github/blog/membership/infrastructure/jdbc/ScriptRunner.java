package com.github.blog.membership.infrastructure.jdbc;

import org.apache.commons.dbutils.QueryRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;

final class ScriptRunner {

  private final QueryRunner queryRunner;
  private final List<String> scripts = new ArrayList<>();

  private Path root = Paths.get(".");

  private ScriptRunner(DataSource dataSource) {
    queryRunner = new QueryRunner(dataSource);
  }

  public static ScriptRunner from(DataSource dataSource) {
    return new ScriptRunner(dataSource);
  }

  public ScriptRunner withRoot(Path path) {
    root = path;
    return this;
  }

  public ScriptRunner add(Path path) throws IOException {
    for (String sql : new String(Files.readAllBytes(root.resolve(path)))
        .split(";")) {

      sql = sql.trim();
      if (!sql.isEmpty()) {
        scripts.add(sql);
      }
    }

    return this;
  }

  public ScriptRunner execute() throws SQLException {
    for (String sql : scripts) {
      queryRunner.execute(sql);
    }

    return this;
  }
}
