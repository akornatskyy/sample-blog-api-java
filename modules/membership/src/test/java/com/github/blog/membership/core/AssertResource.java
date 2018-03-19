package com.github.blog.membership.core;

import org.testng.Assert;

public final class AssertResource {
  private AssertResource() {
  }

  public static void assertEquals(String actual, String resource) {
    Object expected = JsonResource.getResourceAsValue(
        Object.class, resource);
    Assert.assertEquals(
        JsonResource.pretty(actual),
        JsonResource.getValueAsString(expected),
        "Sample: " + resource);
  }

  public static void assertEquals(Object actual, String resource) {
    Object expected = JsonResource.getResourceAsValue(
        actual.getClass(), resource);
    Assert.assertEquals(
        JsonResource.getValueAsString(actual),
        JsonResource.getValueAsString(expected),
        "Sample: " + resource);
  }
}