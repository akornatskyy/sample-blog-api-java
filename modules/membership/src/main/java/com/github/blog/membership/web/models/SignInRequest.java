package com.github.blog.membership.web.models;

import com.github.blog.shared.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public final class SignInRequest {

  @NotEmpty
  @Size(min = 2, max = 20)
  private String username;

  @NotEmpty
  @Size(min = 8, max = 12)
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