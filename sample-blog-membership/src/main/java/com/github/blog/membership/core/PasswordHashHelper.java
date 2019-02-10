package com.github.blog.membership.core;

import com.github.blog.shared.crypto.PasswordHash;

import java.util.Base64;

final class PasswordHashHelper {

  private static final PasswordHash PASSWORD_HASH = new PasswordHash(
      "PBKDF2WithHmacSHA1", 65536, 128);
  private static final Base64.Encoder ENCODER = Base64.getEncoder();
  private static final Base64.Decoder DECODER = Base64.getDecoder();

  private PasswordHashHelper() {
  }

  public static boolean isSamePassword(String passwordHash, String password) {
    return PASSWORD_HASH.compareHashAndPassword(
        DECODER.decode(passwordHash),
        password.toCharArray());
  }

  public static String generateFromPassword(String password) {
    return ENCODER.encodeToString(
        PASSWORD_HASH.generateFromPassword(password.toCharArray()));
  }
}
