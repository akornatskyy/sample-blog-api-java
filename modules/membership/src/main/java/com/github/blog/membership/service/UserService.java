package com.github.blog.membership.service;

import com.github.blog.membership.models.Registration;

public interface UserService {
  boolean authenticate(String username, String password);

  boolean createAccount(Registration registration);
}
