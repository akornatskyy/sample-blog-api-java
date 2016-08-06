package com.github.blog.api;

import com.github.blog.membership.web.SignInFacade;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {

    @Bean
    @Scope("prototype")
    public SignInFacade signInFacade() {
        return new SignInFacade();
    }
}