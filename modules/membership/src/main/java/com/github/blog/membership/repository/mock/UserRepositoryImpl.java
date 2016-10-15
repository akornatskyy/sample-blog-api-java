package com.github.blog.membership.repository.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.blog.membership.models.AuthInfo;
import com.github.blog.membership.repository.UserRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.StreamSupport;

public class UserRepositoryImpl implements UserRepository {

  private static final ArrayNode USERS;

  static {
    try (InputStream is = Object.class.getResourceAsStream(
        "/user-samples.json")) {
      USERS = (ArrayNode) new ObjectMapper().readTree(is).get("users");
    } catch (IOException ex) {
      throw new IllegalStateException("Cannot read user samples.");
    }
  }

  @Override
  public AuthInfo findAuthInfo(String username) {
    return StreamSupport.stream(USERS.spliterator(), false)
        .filter(j -> j.get("username").asText().equals(username))
        .findFirst()
        .map(UserTranslator::authInfo)
        .orElse(null);
  }

  @Override
  public boolean hasAccount(String username) {
    return StreamSupport.stream(USERS.spliterator(), false)
        .anyMatch(j -> j.get("username").asText().equals(username));
  }
}