package com.github.blog.membership.core;

import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.UserService;
import com.github.blog.membership.core.SignInRequest;
import com.github.blog.membership.core.SignInResponse;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public final class SignInFacadeTest {

  @Mock
  private UserService mockUserService;

  private SignInRequest request;
  private ValidationService validationService;
  private SignInFacade signInFacade;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    request = new SignInRequest();
    request.setUsername("demo");
    request.setPassword("password");
    validationService = new ValidationService(new ErrorState());
    signInFacade = new SignInFacade(validationService, mockUserService);
  }

  @AfterMethod
  public void tearDown() {
    Mockito.verify(mockUserService).authenticate(
        request.getUsername(), request.getPassword());
  }

  @Test
  public void testAuthenticateFailed() {
    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertEquals(response, SignInResponse.ERROR);
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