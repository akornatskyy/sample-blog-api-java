package com.github.blog.api.membership;

import com.github.blog.membership.Factory;
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
public final class SignUpController {

  private final Factory factory;

  public SignUpController(final Factory factory) {
    this.factory = factory;
  }

  /**
   * Responds to HTTP POST signup request.
   */
  @RequestMapping(value = "/signup", method = RequestMethod.POST)
  public ResponseEntity<?> post(@RequestBody SignUpRequest request) {

    ErrorState errorState = new ErrorState();
    SignUpFacade facade = factory.createSignUpFacade(errorState);

    SignUpResponse response = facade.createAccount(request);

    if (response == SignUpResponse.ERROR) {
      return new ResponseEntity<>(errorState, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(response, HttpStatus.OK);
  }
}