package com.eaa.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ClientRegistrations;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.oidc.OidcScopes;

/**
 * Programmatic equivalent of the {@code spring.security.oauth2.client.*} properties,
 * registering the "client" OAuth2 client that matches the RegisteredClient configured
 * on the authorization server (see ClientConfig in the authorization-server project).
 */
@Configuration
public class OAuth2ClientConfig {

    private static final String ISSUER_URI = "http://localhost:9090";

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        // Discovers authorization/token/jwks/userinfo endpoints from the issuer's
        // /.well-known/openid-configuration, same as the issuer-uri property would.
        // fromIssuerLocation returns a Builder (clientId isn't part of OIDC metadata),
        // so it must be customized before the single build() call at the end.
        ClientRegistration client = ClientRegistrations.fromIssuerLocation(ISSUER_URI)
                .registrationId("client")
                .clientId("client")
                .clientSecret("secret")
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/client")
                .scope(OidcScopes.OPENID, OidcScopes.PROFILE)
                .build();

        return new InMemoryClientRegistrationRepository(client);
    }
}
