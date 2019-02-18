package com.github.blog.shared;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Properties;

import static org.testng.Assert.*;

public class PropertiesLoaderTest {

  @Test
  public void testFromResource() {
    Properties properties = PropertiesLoader.fromResource(
        "ValidationMessages.properties");

    Assert.assertTrue(properties.size() > 0);
  }

  @Test
  public void testFromResourceUnknown() {
    Properties properties = PropertiesLoader.fromResource("X");

    Assert.assertEquals(properties.size(), 0);
  }
}