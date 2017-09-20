package com.github.blog.api.membership;

import com.github.blog.membership.Factory;
import com.github.blog.membership.web.SignInFacade;
import com.github.blog.membership.web.SignUpFacade;
import com.github.blog.membership.web.models.SignInResponse;
import com.github.blog.membership.web.models.SignUpRequest;
import com.github.blog.membership.web.models.SignUpResponse;
import com.github.blog.shared.service.ErrorState;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Controller
public final class SignUpController {

  private final Factory factory;

  public SignUpController(final Factory factory) {
    this.factory = factory;
  }

  /**
   * Responds to HTTP POST signup request.
   */
  @RequestMapping(value = "/api/v1/signup", method = RequestMethod.POST)
  public ResponseEntity<?> post(
      @RequestBody @Valid SignUpRequest request) {

    ErrorState errorState = new ErrorState();
    SignUpFacade facade = factory.createSignUpFacade(errorState);

    SignUpResponse response = facade.createAccount(request);

    if (response == SignUpResponse.ERROR) {
      return ResponseEntity.badRequest().body(errorState);
    }

    return ResponseEntity.ok(response);
  }
}