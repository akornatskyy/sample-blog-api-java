package com.github.blog.membership.infrastructure.mock;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.blog.membership.core.AuthInfo;

final class UserTranslator {
  private UserTranslator() {
  }

  public static AuthInfo authInfo(JsonNode node) {
    AuthInfo authInfo = new AuthInfo();
    authInfo.setUserId(node.get("id").asText());
    authInfo.setPassword(node.get("password").asText());
    authInfo.setLocked(node.get("is_locked").asBoolean());
    return authInfo;
  }
}