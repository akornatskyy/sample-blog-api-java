package com.github.blog.membership.core;

import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public final class SignUpFacadeTest {

  private ErrorState errorState;
  private ValidationService validationService;
  private UserRepository mockUserRepository;
  private SignUpFacade signUpFacade;

  @BeforeMethod
  public void setUp() {
    errorState = new ErrorState();
    validationService = new ValidationService(errorState);
    mockUserRepository = Mockito.mock(UserRepository.class);
    signUpFacade = new SignUpFacade(
        errorState, validationService, mockUserRepository);
  }

  @Test
  public void testValidationFailed() {
    SignUpRequest request = new SignUpRequest();

    SignUpResponse response = signUpFacade.createAccount(request);

    Assert.assertEquals(response, SignUpResponse.ERROR);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testAlreadyRegistered() {
    SignUpRequest request = sampleRequest();
    Mockito.when(mockUserRepository.hasAccount(request.getUsername()))
        .thenReturn(true);

    SignUpResponse response = signUpFacade.createAccount(request);

    Assert.assertEquals(response, SignUpResponse.ERROR);
    Assert.assertTrue(errorState.hasErrors());
    Assert.assertTrue(errorState.getErrors().containsKey("username"));
  }

  @Test
  public void testCreateAccountUnable() {
    SignUpRequest request = sampleRequest();
    Mockito.when(mockUserRepository.hasAccount(request.getUsername()))
        .thenReturn(false);
    Mockito.when(mockUserRepository.createAccount(Mockito.any()))
        .thenReturn(false);

    SignUpResponse response = signUpFacade.createAccount(request);

    Assert.assertEquals(response, SignUpResponse.ERROR);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testCreateAccount() {
    SignUpRequest request = sampleRequest();
    Mockito.when(mockUserRepository.hasAccount(request.getUsername()))
        .thenReturn(false);
    Mockito.when(mockUserRepository.createAccount(Mockito.any()))
        .thenReturn(true);

    SignUpResponse response = signUpFacade.createAccount(request);

    Assert.assertEquals(response, SignUpResponse.OK);
    Assert.assertFalse(errorState.hasErrors());
  }

  private static SignUpRequest sampleRequest() {
    SignUpRequest request = new SignUpRequest();
    request.setUsername("demo");
    request.setEmail("demo@email.com");
    request.setPassword("password");
    request.setConfirmPassword("password");
    return request;
  }
}