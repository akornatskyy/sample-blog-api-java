package com.github.blog.aws.lambda;

final class Router {
  private Router() {
  }

  public static String match(HttpRequest request) {
    String path = request.getPath();
    if (path == null) {
      return null;
    }

    return RouteNames.WELCOME;
  }
}