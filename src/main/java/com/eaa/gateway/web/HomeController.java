package com.eaa.gateway.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal OidcUser oidcUser) {
        // The authorization server doesn't populate the "preferred_username" claim,
        // so fall back to "sub" (the authenticated username) via getSubject().
        return "Hello, " + oidcUser.getSubject() + "!";
    }
}
