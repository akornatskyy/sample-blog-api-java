package com.github.blog.membership.web;

import java.io.IOException;
import java.io.InputStream;

class TestCaseResourceLoader {
  public static <T> T getResourceAsValue(Class<T> cls, String name) {
    try (InputStream stream = TestCaseResourceLoader.class
        .getResourceAsStream(name)) {
      return TestCaseObjectMapper.MAPPER.readValue(stream, cls);
    } catch (IOException ex) {
      throw new IllegalArgumentException(name, ex);
    }
  }
}