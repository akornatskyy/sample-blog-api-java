package com.github.blog.membership.web.models;

public final class SignInResponse {
  
  public static final SignInResponse ERROR = new SignInResponse();

  private String username;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}