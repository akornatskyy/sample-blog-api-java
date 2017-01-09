package com.github.blog.aws.lambda;

import com.github.blog.shared.PropertiesLoader;

import java.util.Map;

public class FactoryProvider {
  private static final String ENVIRONMENT = "env";
  private static final String DEFAULT_ENVIRONMENT = "dev";

  private static volatile Factory factory;

  public Factory from(Map<String, String> context) {
    if (factory == null) {
      synchronized (FactoryProvider.class) {
        if (factory == null) {
          String name = context.getOrDefault(ENVIRONMENT, DEFAULT_ENVIRONMENT);
          factory = new Factory(
              PropertiesLoader.fromResource(name + ".properties"));
        }
      }
    }

    return factory;
  }
}