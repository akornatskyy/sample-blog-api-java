package com.github.blog.shared.crypto;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public final class PasswordHash {

  private final int iterationCount;
  private final int keyLength;
  private final int saltLength;
  private final SecretKeyFactory secretKeyFactory;

  /**
   * Constructs password hash.
   */
  public PasswordHash(String algorithm, int iterationCount, int keyLength)
      throws NoSuchAlgorithmException {

    this.iterationCount = iterationCount;
    this.keyLength = keyLength;
    saltLength = keyLength / 8;
    secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
  }

  /**
   * Generate a hash from password.
   */
  @SuppressWarnings("PMD.UseVarargs")
  public byte[] generateFromPassword(char[] password)
      throws InvalidKeySpecException {

    SecureRandom random = new SecureRandom();
    byte[] salt = new byte[saltLength];
    random.nextBytes(salt);
    KeySpec spec = new PBEKeySpec(password, salt, iterationCount, keyLength);
    byte[] secret = secretKeyFactory.generateSecret(spec).getEncoded();
    byte[] hashed = new byte[salt.length + secret.length];
    System.arraycopy(salt, 0, hashed, 0, salt.length);
    System.arraycopy(secret, 0, hashed, salt.length, secret.length);
    return hashed;
  }

  /**
   * Compare hash and password.
   */
  @SuppressWarnings("PMD.UseVarargs")
  public boolean compareHashAndPassword(byte[] hashed, char[] password)
      throws InvalidKeySpecException {

    if (hashed == null || password == null || hashed.length != 2 * saltLength) {
      return false;
    }

    byte[] buffer = new byte[saltLength];
    System.arraycopy(hashed, 0, buffer, 0, buffer.length);
    KeySpec spec = new PBEKeySpec(password, buffer, iterationCount, keyLength);
    byte[] hash = secretKeyFactory.generateSecret(spec).getEncoded();
    System.arraycopy(hashed, saltLength, buffer, 0, buffer.length);
    return Arrays.equals(hash, buffer);
  }
}
