package com.github.blog.membership.core;

import com.github.blog.shared.validator.constraints.Length;

public final class SignUpRequest {

  @Length(min = 6, max = 50)
  private String email;

  @Length(min = 2, max = 20)
  private String username;

  @Length(min = 8, max = 12)
  private String password;

  private String confirmPassword;

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

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

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}