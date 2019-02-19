package com.github.blog.shared.crypto;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Base64;

public class PasswordHashTest {

  private final PasswordHash passwordHash = new PasswordHash(
      "PBKDF2WithHmacSHA1", 65536, 128);

  @DataProvider
  public Object[][] samples() {
    return new Object[][] {
        {"", "", false},
        {"cGFzc3dvcmRwYXNzd29yZA==", "", false},
        {"MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDE=", "", false},
        {"MDEyMzQ1Njc4OTAxMjM0NTY3ODkwMTIzNDU2Nzg5MDEyMw==", "", false},

        {"SIP0VqRjrz7Hq4I7Vu7LbMQN/TJsT5iSR35OxZyb0N0=", "", true},
        {"4OZWM3RdfdZRT7TFo4/SzUDp7qv55QggBODf1iFHDmk=", "", true},
        {"jVMM0FqCoEebmaxJgqOaK3xpWltrrOXZNBE1MG/r7iU=", "test", true},
        {"4LqMgSlBM9wj+qSFZ4U85fv3LlyA/4Sb0iv0/PZgJN4=", "password", true}
    };
  }

  @Test(dataProvider = "samples")
  public void testCompareHashAndPassword(
      String hash, String password, boolean expected) {

    boolean ok = passwordHash.compareHashAndPassword(
        Base64.getDecoder().decode(hash), password.toCharArray());

    Assert.assertEquals(ok, expected);
  }

  @Test
  public void testGenerateFromPassword() {
    byte[] hashed = passwordHash.generateFromPassword("password".toCharArray());

    Assert.assertNotNull(hashed);
    Assert.assertEquals(32, hashed.length);
  }

  @Test(expectedExceptions = IllegalStateException.class)
  public void testConstructor() {
    new PasswordHash("X", 0, 0);
  }
}