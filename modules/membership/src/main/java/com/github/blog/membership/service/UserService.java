package com.github.blog.membership.service;

public interface UserService {
  boolean authenticate(String username, String password);
}
