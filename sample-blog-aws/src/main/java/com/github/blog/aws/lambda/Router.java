package com.github.blog.aws.lambda;

import java.util.Locale;

final class Router {
  private Router() {
  }

  public static String match(HttpRequest request) {
    String path = request.getPath();
    if (path == null) {
      return null;
    }

    path = path.toLowerCase(Locale.ENGLISH);
    switch (request.getHttpMethod()) {
      case "GET":
        return matchHttpGet(path);
      case "POST":
        return matchHttpPost(path);
      default:
        return null;
    }
  }

  @SuppressWarnings("PMD.AvoidLiteralsInIfCondition")
  private static String matchHttpGet(String path) {
    if ("/welcome".equals(path)) {
      return RouteNames.WELCOME;
    }

    return null;
  }

  private static String matchHttpPost(String path) {
    switch (path) {
      case "/signin":
        return RouteNames.SIGNIN;
      case "/signup":
        return RouteNames.SIGNUP;
      default:
        return null;
    }
  }
}