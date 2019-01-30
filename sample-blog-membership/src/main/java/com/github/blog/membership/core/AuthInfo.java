package com.github.blog.membership.core;

import java.util.Objects;

public final class AuthInfo {
  private String userId;
  private String password;
  private boolean locked;

  public boolean isSamePassword(String password) {
    return Objects.equals(this.password, password);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }
}