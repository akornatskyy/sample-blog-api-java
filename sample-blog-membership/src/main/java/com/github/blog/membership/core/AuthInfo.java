package com.github.blog.membership.core;

public final class AuthInfo {

  private String userId;
  private String passwordHash;
  private boolean locked;

  /**
   * Checks if password is the same as password hash.
   */
  public boolean isSamePassword(String password) {
    return PasswordHashHelper.isSamePassword(passwordHash, password);
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }

  public boolean isLocked() {
    return locked;
  }

  public void setLocked(boolean locked) {
    this.locked = locked;
  }
}