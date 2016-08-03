package com.github.blog.api.membership;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class SignInController {

    @RequestMapping(method = RequestMethod.GET)
    public String get() {
        return "Hello World!";
    }
}