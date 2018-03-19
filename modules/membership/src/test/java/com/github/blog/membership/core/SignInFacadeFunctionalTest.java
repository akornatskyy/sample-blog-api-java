package com.github.blog.membership.core;

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
        {12, "request for locked account"},
        {21, "request for authorized user"}
    };
  }

  @Test(dataProvider = "samples")
  public void testAuthenticate(int sample, String description) {
    SignInRequest signInRequest = JsonResource.getResourceAsValue(
        SignInRequest.class,
        formatResourceName(sample, "SignInRequest"));
    AuthInfo authInfo = JsonResource.getResourceAsValue(
        AuthInfo.class,
        formatResourceName(sample, "AuthInfo"));
    UserRepository mockUserRepository = Mockito.mock(UserRepository.class);
    Mockito.when(mockUserRepository.findAuthInfo(signInRequest.getUsername()))
        .thenReturn(authInfo);
    ErrorState errorState = new ErrorState();
    SignInFacade signInFacade = new SignInFacade(
        errorState,
        new ValidationService(errorState), mockUserRepository
    );

    SignInResponse signInResponse = signInFacade.authenticate(signInRequest);

    Assert.assertNotNull(signInResponse, description);
    AssertResource.assertEquals(
        signInResponse,
        formatResourceName(sample, "SignInResponse"));
    AssertResource.assertEquals(
        errorState,
        formatResourceName(sample, "ErrorState"));
  }

  private static String formatResourceName(int sample, String name) {
    return String.format("/stories/signin/%02d. %s.json", sample, name);
  }
}