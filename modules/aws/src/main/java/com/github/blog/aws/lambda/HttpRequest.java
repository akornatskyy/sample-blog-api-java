package com.github.blog.aws.lambda;

import java.util.Map;

public final class HttpRequest {
  private String httpMethod;
  private String path;
  private Map<String, String> queryStringParameters;
  private Map<String, String> headers;
  private Map<String, String> stageVariables;
  private String body;
  private Boolean isBase64Encoded;

  public String getHttpMethod() {
    return httpMethod;
  }

  public void setHttpMethod(String httpMethod) {
    this.httpMethod = httpMethod;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public Map<String, String> getQueryStringParameters() {
    return queryStringParameters;
  }

  public void setQueryStringParameters(Map<String, String> queryStringParameters) {
    this.queryStringParameters = queryStringParameters;
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

  public Map<String, String> getStageVariables() {
    return stageVariables;
  }

  public void setStageVariables(Map<String, String> stageVariables) {
    this.stageVariables = stageVariables;
  }

  public Boolean getBase64Encoded() {
    return isBase64Encoded;
  }

  public void setBase64Encoded(Boolean base64Encoded) {
    isBase64Encoded = base64Encoded;
  }
}