package com.github.blog.membership.service;

import com.github.blog.membership.models.AuthInfo;
import com.github.blog.membership.models.Registration;
import com.github.blog.membership.repository.UserRepository;
import com.github.blog.shared.service.ErrorState;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UserServiceBridgeTest {

  @Mock
  private UserRepository mockUserRepository;

  private ErrorState errorState;
  private UserService userService;

  @BeforeMethod
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    errorState = new ErrorState();
    userService = new UserServiceBridge(
        errorState,
        mockUserRepository);
  }

  @Test
  public void testAuthenticateUserNotFound() {
    Mockito.when(mockUserRepository.findAuthInfo("user"))
        .thenReturn(null);

    boolean succeed = userService.authenticate("user", "");

    Assert.assertFalse(succeed);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testAuthenticateWrongPassword() {
    AuthInfo authInfo = new AuthInfo();
    authInfo.setPassword("password");
    Mockito.when(mockUserRepository.findAuthInfo("user"))
        .thenReturn(authInfo);

    boolean succeed = userService.authenticate("user", "invalid");

    Assert.assertFalse(succeed);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testAuthenticateLocked() {
    AuthInfo authInfo = new AuthInfo();
    authInfo.setPassword("password");
    authInfo.setLocked(true);
    Mockito.when(mockUserRepository.findAuthInfo("user"))
        .thenReturn(authInfo);

    boolean succeed = userService.authenticate("user", "password");

    Assert.assertFalse(succeed);
    Assert.assertTrue(errorState.hasErrors());
  }

  @Test
  public void testAuthenticate() {
    AuthInfo authInfo = new AuthInfo();
    authInfo.setPassword("password");
    authInfo.setLocked(false);
    Mockito.when(mockUserRepository.findAuthInfo("user"))
        .thenReturn(authInfo);

    boolean succeed = userService.authenticate("user", "password");

    Assert.assertTrue(succeed);
    Assert.assertFalse(errorState.hasErrors());
  }

  @Test
  public void testCreateAccountAlreadyRegistered() {
    Registration registration = new Registration();
    registration.setUsername("demo");
    Mockito.when(mockUserRepository.hasAccount(registration.getUsername()))
        .thenReturn(true);

    boolean succeed = userService.createAccount(registration);

    Assert.assertFalse(succeed);
    Assert.assertTrue(errorState.hasErrors());
    Assert.assertTrue(errorState.getErrors().containsKey("username"));
  }

  @Test
  public void testCreateAccountUnable() {
    Registration registration = new Registration();
    registration.setUsername("demo");
    Mockito.when(mockUserRepository.hasAccount(registration.getUsername()))
        .thenReturn(false);
    Mockito.when(mockUserRepository.createAccount(registration))
        .thenReturn(false);

    boolean succeed = userService.createAccount(registration);

    Assert.assertFalse(succeed);
    Assert.assertTrue(errorState.hasErrors());
  }
}