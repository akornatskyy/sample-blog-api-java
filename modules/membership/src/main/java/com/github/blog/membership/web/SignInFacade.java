package com.github.blog.membership.web;

import com.github.blog.membership.web.models.SignInRequest;
import com.github.blog.membership.web.models.SignInResponse;

public final class SignInFacade {
    public SignInResponse authenticate(SignInRequest request) {
        if (request.getUsername() == null) {
            return null;
        }

        SignInResponse response = new SignInResponse();
        response.setUsername(request.getUsername());
        return response;
    }
}