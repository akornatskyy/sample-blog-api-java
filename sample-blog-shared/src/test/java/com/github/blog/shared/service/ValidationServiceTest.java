package com.github.blog.shared.service;

import com.github.blog.shared.validator.constraints.Length;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class ValidationServiceTest {

  private ErrorState errorState;
  private ValidationService validationService;

  @BeforeMethod
  public void setUp() {
    errorState = new ErrorState();
    validationService = new ValidationService(errorState);
  }

  @Test
  public void testValidate() {
    boolean ok = validationService.validate(new Object() {
      @Length(min = 1)
      String name = "X";
    });

    Assert.assertTrue(ok);
    Assert.assertFalse(errorState.hasErrors());
  }

  @Test
  public void testValidateAddError() {
    boolean ok = validationService.validate(new Object() {
      @Length(min = 1)
      String name;
    });

    Assert.assertFalse(ok);
    Assert.assertTrue(errorState.hasErrors());
  }
}