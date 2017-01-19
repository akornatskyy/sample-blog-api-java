package com.github.blog.aws.lambda;

final class Router {
  private Router() {
  }

  public static String match(HttpRequest request) {
    String path = request.getPath();
    if (path == null) {
      return null;
    }

    if ("/".equals(path)) {
      return RouteNames.WELCOME;
    }

    path = path.toLowerCase();
    if (!path.startsWith("/v1/")) {
      return null;
    }

    path = path.substring(4);
    switch (path) {
      case "signin":
        return RouteNames.SIGNIN;
      default:
        return null;
    }
  }
}