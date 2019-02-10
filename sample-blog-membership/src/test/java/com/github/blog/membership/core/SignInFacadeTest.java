package com.github.blog.membership.core;

import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.security.spec.InvalidKeySpecException;

public final class SignInFacadeTest {

  private ErrorState errorState;
  private ValidationService validationService;
  private UserRepository mockUserRepository;
  private SignInFacade signInFacade;

  @BeforeMethod
  public void setUp() {
    errorState = new ErrorState();
    validationService = new ValidationService(errorState);
    mockUserRepository = Mockito.mock(UserRepository.class);
    signInFacade = new SignInFacade(
        errorState, validationService, mockUserRepository);
  }

  @Test
  public void testValidationFailed() {
    SignInRequest request = new SignInRequest();

    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertEquals(response, SignInResponse.ERROR);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testUserNotFound() {
    SignInRequest request = sampleRequest();
    Mockito.when(mockUserRepository.findAuthInfo("user"))
        .thenReturn(null);

    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertEquals(response, SignInResponse.ERROR);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testWrongPassword() {
    SignInRequest request = sampleRequest();
    request.setPassword("invalid");
    AuthInfo authInfo = new AuthInfo();
    authInfo.setPasswordHash(
        PasswordHashHelper.generateFromPassword("password"));
    Mockito.when(mockUserRepository.findAuthInfo("user"))
        .thenReturn(authInfo);

    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertEquals(response, SignInResponse.ERROR);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testLocked() {
    SignInRequest request = sampleRequest();
    AuthInfo authInfo = new AuthInfo();
    authInfo.setPasswordHash(
        PasswordHashHelper.generateFromPassword(request.getPassword()));
    authInfo.setLocked(true);
    Mockito.when(mockUserRepository.findAuthInfo(request.getUsername()))
        .thenReturn(authInfo);

    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertEquals(response, SignInResponse.ERROR);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testAuthenticate() {
    SignInRequest request = sampleRequest();
    AuthInfo authInfo = new AuthInfo();
    authInfo.setPasswordHash(
        PasswordHashHelper.generateFromPassword(request.getPassword()));
    Mockito.when(mockUserRepository.findAuthInfo(request.getUsername()))
        .thenReturn(authInfo);

    SignInResponse response = signInFacade.authenticate(request);

    Assert.assertNotNull(response);
    Assert.assertFalse(errorState.hasErrors());
  }

  private static SignInRequest sampleRequest() {
    SignInRequest request = new SignInRequest();
    request.setUsername("user");
    request.setPassword("password");
    return request;
  }
}