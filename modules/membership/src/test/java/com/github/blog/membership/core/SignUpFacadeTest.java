package com.github.blog.membership.core;

import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public final class SignUpFacadeTest {

  @Mock
  private UserService mockUserService;

  private ValidationService validationService;
  private SignUpFacade signUpFacade;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);

    validationService = new ValidationService(new ErrorState());
    signUpFacade = new SignUpFacade(validationService, mockUserService);
  }

  @Test
  public void testCreateAccountFailed() {
    Mockito.when(mockUserService.createAccount(Mockito.any()))
        .thenReturn(false);
    SignUpRequest request = new SignUpRequest();

    SignUpResponse response = signUpFacade.createAccount(request);

    Assert.assertNotNull(response);
    Assert.assertEquals(response, SignUpResponse.ERROR);
  }

  @Test
  public void testCreateAccount() {
    Mockito.when(mockUserService.createAccount(Mockito.any()))
        .thenReturn(true);
    SignUpRequest request = new SignUpRequest();
    request.setUsername("demo");
    request.setEmail("demo@email.com");
    request.setPassword("password");
    request.setConfirmPassword("password");

    SignUpResponse response = signUpFacade.createAccount(request);

    Assert.assertEquals(response, SignUpResponse.OK);
    Mockito.verify(mockUserService).createAccount(Mockito.any());
  }
}