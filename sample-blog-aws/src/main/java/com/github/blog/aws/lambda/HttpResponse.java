package com.github.blog.aws.lambda;

import java.util.HashMap;
import java.util.Map;

public final class HttpResponse {
  public static final HttpResponse NOT_FOUND;
  public static final HttpResponse INTERNAL_SERVER_ERROR;

  static {
    NOT_FOUND = new HttpResponse();
    NOT_FOUND.setStatusCode(404);
    INTERNAL_SERVER_ERROR = new HttpResponse();
    INTERNAL_SERVER_ERROR.setStatusCode(500);
    INTERNAL_SERVER_ERROR.setBody(
        "The server encountered an unexpected condition which "
        + "prevented it from fulfilling the request.");
  }

  private int statusCode = 200;
  private Map<String, String> headers = new HashMap<>();
  private String body;

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }
}