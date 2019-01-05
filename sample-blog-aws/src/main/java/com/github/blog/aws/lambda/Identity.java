package com.github.blog.aws.lambda;

public final class Identity {
  private String sourceIp;

  public String getSourceIp() {
    return sourceIp;
  }

  public void setSourceIp(String sourceIp) {
    this.sourceIp = sourceIp;
  }
}