package com.github.blog.membership.service;

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
}