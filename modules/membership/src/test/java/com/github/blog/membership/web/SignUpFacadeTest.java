package com.github.blog.membership.web;

import com.github.blog.membership.service.UserService;
import com.github.blog.membership.web.models.SignUpRequest;
import com.github.blog.membership.web.models.SignUpResponse;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignUpFacadeTest {

  @Mock
  private UserService mockUserService;

  private SignUpRequest request;
  private SignUpFacade signUpFacade;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    request = new SignUpRequest();
    request.setUsername("demo");
    signUpFacade = new SignUpFacade(mockUserService);
  }

  @AfterMethod
  public void tearDown() {
    Mockito.verify(mockUserService).createAccount(Mockito.any());
  }

  @Test
  public void testCreateAccountFailed() {
    Mockito.when(mockUserService.createAccount(Mockito.any()))
        .thenReturn(false);

    SignUpResponse response = signUpFacade.createAccount(request);

    Assert.assertNull(response);
  }
}