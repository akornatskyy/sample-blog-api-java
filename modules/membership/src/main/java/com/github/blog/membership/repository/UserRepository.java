package com.github.blog.membership.repository;

import com.github.blog.membership.models.AuthInfo;
import com.github.blog.membership.models.Registration;

public interface UserRepository {
  AuthInfo findAuthInfo(String username);

  boolean hasAccount(String username);

  boolean createAccount(Registration registration);
}