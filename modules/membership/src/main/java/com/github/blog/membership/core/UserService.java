package com.github.blog.membership.core;

public interface UserService {
  boolean authenticate(String username, String password);

  boolean createAccount(Registration registration);
}
