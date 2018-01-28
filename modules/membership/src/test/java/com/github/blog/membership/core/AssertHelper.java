package com.github.blog.membership.core;

import org.testng.Assert;

final class AssertHelper {
  public static void assertSampleEquals(Object actual, String sample) {
    Object expected = TestCaseResourceLoader.getResourceAsValue(
        actual.getClass(), sample);
    Assert.assertEquals(
        TestCaseObjectMapper.getValueAsString(actual),
        TestCaseObjectMapper.getValueAsString(expected),
        "Sample: " + sample);
  }
}