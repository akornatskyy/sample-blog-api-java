package com.github.blog.aws.lambda;

import java.util.HashMap;
import java.util.Map;

public final class HttpResponse {

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