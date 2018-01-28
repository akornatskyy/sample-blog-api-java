package com.github.blog.membership.core;

import com.github.blog.shared.validator.constraints.Length;

public final class SignInRequest {

  @Length(min = 2, max = 20)
  private String username;

  @Length(min = 8, max = 12)
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}