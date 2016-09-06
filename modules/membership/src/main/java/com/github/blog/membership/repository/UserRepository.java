package com.github.blog.membership.repository;

import com.github.blog.membership.models.AuthInfo;

public interface UserRepository {
  AuthInfo findAuthInfo(String username);
}