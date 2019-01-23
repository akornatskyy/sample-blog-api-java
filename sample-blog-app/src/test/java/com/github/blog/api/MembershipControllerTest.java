package com.github.blog.api;

import com.github.blog.membership.Factory;
import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignInRequest;
import com.github.blog.membership.core.SignInResponse;
import com.github.blog.shared.service.ErrorState;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MembershipControllerTest {

  private final Factory mockFactory = Mockito.mock(Factory.class);
  private final ArgumentCaptor<ErrorState> errorStateCaptor =
      ArgumentCaptor.forClass(ErrorState.class);
  private final MembershipController controller =
      new MembershipController(mockFactory);

  @Test
  public void testSignIn() {
    SignInRequest request = new SignInRequest();
    SignInResponse response = new SignInResponse();
    SignInFacade mockSignInFacade = Mockito.mock(SignInFacade.class);
    Mockito.when(mockSignInFacade.authenticate(request))
        .thenReturn(response);
    Mockito.when(mockFactory.createSignInFacade(Mockito.any()))
        .thenReturn(mockSignInFacade);

    ResponseEntity<?> entity = controller.signIn(request);

    Assert.assertNotNull(entity);
    Assert.assertEquals(entity.getStatusCode(), HttpStatus.OK);
    Assert.assertEquals(entity.getBody(), response);
  }

  @Test
  public void testSignInError() {
    SignInRequest request = new SignInRequest();
    SignInFacade mockSignInFacade = Mockito.mock(SignInFacade.class);
    Mockito.when(mockSignInFacade.authenticate(request))
        .thenReturn(SignInResponse.ERROR);
    Mockito.when(mockFactory.createSignInFacade(errorStateCaptor.capture()))
        .thenReturn(mockSignInFacade);

    ResponseEntity<?> entity = controller.signIn(request);

    Assert.assertNotNull(entity);
    Assert.assertEquals(entity.getStatusCode(), HttpStatus.BAD_REQUEST);
    Assert.assertEquals(entity.getBody(), errorStateCaptor.getValue());
  }
}