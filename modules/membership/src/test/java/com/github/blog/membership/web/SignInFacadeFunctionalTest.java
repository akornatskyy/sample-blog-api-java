package com.github.blog.membership.web;

import com.github.blog.membership.models.AuthInfo;
import com.github.blog.membership.repository.UserRepository;
import com.github.blog.membership.service.UserServiceBridge;
import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;
import com.github.blog.shared.service.ErrorState;
import com.github.blog.shared.service.ValidationService;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public final class SignInFacadeFunctionalTest {

  @DataProvider
  public Object[][] samples() {
    return new Object[][] {
        {1, "a null request causes validation errors"},
        {2, "an empty request causes validation errors"},
        {3, "request cause validation errors"},
        {11, "request with invalid credentials"},
        {12, "request for locked account"}
    };
  }

  @Test(dataProvider = "samples")
  public void testAuthenticate(int sample, String description) {
    SignInRequest signInRequest = TestCaseResourceLoader.getResourceAsValue(
        SignInRequest.class,
        formatResourceName(sample, "SignInRequest"));
    AuthInfo authInfo = TestCaseResourceLoader.getResourceAsValue(
        AuthInfo.class,
        formatResourceName(sample, "AuthInfo"));
    UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    Mockito.when(mockUserRepository.findAuthInfo(signInRequest.getUsername()))
        .thenReturn(authInfo);
    ErrorState errorState = new ErrorState();
    SignInFacade signInFacade = new SignInFacade(
        new ValidationService(errorState),
        new UserServiceBridge(errorState, mockUserRepository));

    SignInResponse signInResponse = signInFacade.authenticate(signInRequest);

    Assert.assertNotNull(signInResponse, description);
    AssertHelper.assertSampleEquals(
        signInResponse,
        formatResourceName(sample, "SignInResponse"));
    AssertHelper.assertSampleEquals(
        errorState,
        formatResourceName(sample, "ErrorState"));
  }

  private static String formatResourceName(int sample, String name) {
    return String.format("/stories/signin/%02d. %s.json", sample, name);
  }
}