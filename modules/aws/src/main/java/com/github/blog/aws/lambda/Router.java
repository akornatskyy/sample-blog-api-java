package com.github.blog.aws.lambda;

final class Router {
  private Router() {
  }

  public static String match(HttpRequest request) {
    String path = request.getPath();
    if (path == null) {
      return null;
    }

    path = path.toLowerCase();
    switch (request.getHttpMethod()) {
      case "GET":
        return matchHttpGet(path);
      case "POST":
        return matchHttpPost(path);
      default:
        return null;
    }
  }

  private static String matchHttpGet(String path) {
    switch (path) {
      case "/welcome":
        return RouteNames.WELCOME;
      default:
        return null;
    }
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