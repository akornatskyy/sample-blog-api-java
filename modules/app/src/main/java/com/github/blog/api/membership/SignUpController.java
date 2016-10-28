package com.github.blog.api.membership;

import com.github.blog.membership.web.SignUpFacade;
import com.github.blog.membership.web.models.SignUpRequest;
import com.github.blog.membership.web.models.SignUpResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Scope("prototype")
public final class SignUpController {

  private final SignUpFacade signUpFacade;

  public SignUpController(final SignUpFacade signUpFacade) {
    this.signUpFacade = signUpFacade;
  }

  /**
   * Responds to HTTP POST signup request.
   */
  @RequestMapping(value = "/api/v1/signup", method = RequestMethod.POST)
  public ResponseEntity<SignUpResponse> post(
      @RequestBody @Valid SignUpRequest request) {
    return ResponseEntity.ok(this.signUpFacade.createAccount(request));
  }
}