package com.github.blog.api;

import com.github.blog.membership.Factory;
import com.github.blog.membership.core.SignInFacade;
import com.github.blog.membership.core.SignInRequest;
import com.github.blog.membership.core.SignInResponse;
import com.github.blog.membership.core.SignUpFacade;
import com.github.blog.membership.core.SignUpRequest;
import com.github.blog.membership.core.SignUpResponse;
import com.github.blog.shared.service.ErrorState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MembershipController {

  private final Factory factory;

  public MembershipController(final Factory factory) {
    this.factory = factory;
  }

  /**
   * Responds to HTTP POST signin request.
   */
  @RequestMapping(value = "/signin", method = RequestMethod.POST)
  public ResponseEntity<?> signIn(@RequestBody SignInRequest request) {

    ErrorState errorState = new ErrorState();
    SignInFacade facade = factory.createSignInFacade(errorState);

    SignInResponse response = facade.authenticate(request);

    if (response == SignInResponse.ERROR) {
      return new ResponseEntity<>(errorState, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }

  /**
   * Responds to HTTP POST signup request.
   */
  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public ResponseEntity<?> signUp(@RequestBody SignUpRequest request) {

    ErrorState errorState = new ErrorState();
    SignUpFacade facade = factory.createSignUpFacade(errorState);

    SignUpResponse response = facade.createAccount(request);

    if (response == SignUpResponse.ERROR) {
      return new ResponseEntity<>(errorState, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}
