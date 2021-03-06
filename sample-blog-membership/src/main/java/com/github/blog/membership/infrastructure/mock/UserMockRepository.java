package com.github.blog.membership.infrastructure.mock;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.blog.membership.core.AuthInfo;
import com.github.blog.membership.core.Registration;
import com.github.blog.membership.core.UserRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.StreamSupport;

public final class UserMockRepository implements UserRepository {

  private static final ArrayNode USERS;

  static {
    try (InputStream is = UserMockRepository.class.getResourceAsStream(
        "/user-samples.json")) {
      USERS = (ArrayNode) new ObjectMapper().readTree(is).get("users");
    } catch (IOException ex) {
      throw new IllegalStateException("Cannot read user samples.", ex);
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

  @Override
  public boolean createAccount(Registration registration) {
    ObjectNode user = USERS.addObject();
    user.put("username", registration.getUsername());
    user.put("email", registration.getEmail());
    user.put("password", registration.getPasswordHash());
    return true;
  }
}