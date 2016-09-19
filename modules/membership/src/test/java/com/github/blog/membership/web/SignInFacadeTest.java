package com.github.blog.membership.web;

import com.github.blog.membership.service.UserService;
import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SignInFacadeTest {

  @Mock
  private UserService mockUserService;

  private SignInRequest request;
  private SignInFacade signInFacade;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    request = new SignInRequest();
    request.setUsername("demo");
    request.setPassword("password");
    signInFacade = new SignInFacade(mockUserService);
  }

  @AfterMethod
  public void tearDown() {
    Mockito.verify(mockUserService).authenticate(
        request.getUsername(), request.getPassword());
  }

  @Test
  public void testAuthenticateFailed() {
    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertNull(response);
    Mockito.verify(mockUserService).authenticate(
        request.getUsername(), request.getPassword());
  }

  @Test
  public void testAuthenticate() {
    Mockito.when(mockUserService.authenticate(
        request.getUsername(), request.getPassword())).thenReturn(true);

    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertNotNull(response);
    Assert.assertEquals(response.getUsername(), request.getUsername());
  }
}