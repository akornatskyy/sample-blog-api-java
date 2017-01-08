package com.github.blog.aws.lambda;

import java.util.Properties;

public class Factory {
  public Factory(Properties properties) {
  }

  public WelcomeFacade createWelcomeFacade() {
    return new WelcomeFacade();
  }
}