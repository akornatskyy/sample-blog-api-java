package com.github.blog.api.membership;

import com.github.blog.membership.web.SignInFacade;
import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
@Scope("prototype")
public final class SignInController {

    private final SignInFacade signInFacade;

    @Inject
    public SignInController(final SignInFacade signInFacade) {
        this.signInFacade = signInFacade;
    }

    @RequestMapping(value = "/api/v1/signin", method = RequestMethod.POST)
    public ResponseEntity<SignInResponse> post(@RequestBody @Valid SignInRequest req) {
        SignInResponse resp = this.signInFacade.authenticate(req);
        if (resp == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}