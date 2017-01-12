package com.github.blog.aws.lambda;

import java.util.HashMap;
import java.util.Map;

final class WelcomeFacade {
  /**
   * Welcome facade processing.
   */
  public Map<String, Object> process(HttpRequest request) {
    return new HashMap<String, Object>() {
      {
        put("message", "Hello World!");
        put("request", request);
      }
    };
  }
}