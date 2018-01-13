package com.github.blog.membership.web;

import org.testng.Assert;

class AssertHelper {
  public static void assertSampleEquals(Object actual, String sample) {
    Object expected = TestCaseResourceLoader.getResourceAsValue(
        actual.getClass(), sample);
    Assert.assertEquals(
        TestCaseObjectMapper.getValueAsString(actual),
        TestCaseObjectMapper.getValueAsString(expected),
        "Sample: " + sample);
  }
}