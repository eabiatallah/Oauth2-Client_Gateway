package com.eaa.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated())
                // Delegates authentication to the OAuth2 authorization server using the
                // "client" registration (authorization_code grant) configured in OAuth2ClientConfig.
                .oauth2Login(Customizer.withDefaults())
                // Logs the user out locally; the authorization server session remains active.
                .logout(Customizer.withDefaults());
        return http.build();
    }
}
