package com.github.blog.shared.service;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class ErrorStateTest {

  private ErrorState errorState;

  @BeforeMethod
  public void setUp() {
    errorState = new ErrorState();
  }

  @Test
  public void testAddError() {
    errorState.addError("X");

    Assert.assertTrue(errorState.hasErrors());
    Map<String, Collection<String>> errors = errorState.getErrors();
    Assert.assertEquals(errors.size(), 1);
    List<String> fieldErrors = (List<String>) errors.get("__ERROR__");
    Assert.assertEquals(fieldErrors.size(), 1);
    Assert.assertTrue(fieldErrors.contains("X"));

    errorState.addError("Y");
    Assert.assertEquals(fieldErrors.size(), 2);
    Assert.assertEquals(fieldErrors.get(0), "X");
    Assert.assertEquals(fieldErrors.get(1), "Y");
  }

  @Test
  public void testAddFieldError() {
    errorState.addError("f1", "X");

    Assert.assertTrue(errorState.hasErrors());
    Map<String, Collection<String>> errors = errorState.getErrors();
    Assert.assertEquals(errors.size(), 1);
    List<String> fieldErrors = (List<String>) errors.get("f1");
    Assert.assertEquals(fieldErrors.size(), 1);
    Assert.assertTrue(fieldErrors.contains("X"));

    errorState.addError("f1", "Y");
    errorState.addError("f2", "A");

    Assert.assertEquals(fieldErrors.size(), 2);
    Assert.assertEquals(fieldErrors.get(0), "X");
    Assert.assertEquals(fieldErrors.get(1), "Y");
    fieldErrors = (List<String>) errors.get("f2");
    Assert.assertEquals(fieldErrors.get(0), "A");
  }
}