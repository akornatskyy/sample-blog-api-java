package com.github.blog.membership.core;

public interface UserRepository {
  AuthInfo findAuthInfo(String username);

  boolean hasAccount(String username);

  boolean createAccount(Registration registration);
}