package com.github.blog.api.membership;

import com.github.blog.membership.Factory;
import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignInRequest;
import com.github.blog.membership.core.SignInResponse;
import com.github.blog.shared.service.ErrorState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public final class SignInController {

  private final Factory factory;

  public SignInController(final Factory factory) {
    this.factory = factory;
  }

  /**
   * Responds to HTTP POST signin request.
   */
  @RequestMapping(value = "/api/v1/signin", method = RequestMethod.POST)
  public ResponseEntity<?> post(@RequestBody SignInRequest request) {

    ErrorState errorState = new ErrorState();
    SignInFacade facade = factory.createSignInFacade(errorState);

    SignInResponse response = facade.authenticate(request);

    if (response == SignInResponse.ERROR) {
      return new ResponseEntity<>(errorState, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}