package com.github.blog.aws.lambda;

import java.util.HashMap;
import java.util.Map;

final class WelcomeFacade {
  /**
   * Welcome facade processing.
   */
  public Map<String, Object> process(HttpRequest request) {
    Map<String, Object> response = new HashMap<>();
    response.put("message", "Hello World!");
    response.put("request", request);
    return response;
  }
}