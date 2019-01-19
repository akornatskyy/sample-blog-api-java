package com.github.blog.shared;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class PropertiesLoader {
  private PropertiesLoader() {
  }

  /**
   * Uses class loader to load properties from resource stream.
   */
  public static Properties fromResource(String name) {
    Properties properties = new Properties();
    try (InputStream in = Thread.currentThread().getContextClassLoader()
        .getResourceAsStream(name)) {
      if (in != null) {
        properties.load(in);
      }
    } catch (IOException ex) {
      throw new IllegalStateException(
          String.format("Unable to load %s file.", name), ex);
    }

    return properties;
  }
}