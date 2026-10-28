package com.eaa.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions.uri;
import static org.springframework.cloud.gateway.server.mvc.filter.TokenRelayFilterFunctions.tokenRelay;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;

/**
 * Routes incoming requests to downstream services (e.g. the resource-server) using
 * Spring Cloud Gateway Server MVC. The {@code tokenRelay()} filter forwards the
 * currently logged-in user's OAuth2 access token (obtained via oauth2Login) to the
 * downstream request's Authorization header, so the resource-server can validate it.
 */
@Configuration
public class GatewayRouteConfig {

    @Bean
    public RouterFunction<ServerResponse> resourceServerRoute() {
        return route("resource-server") // names this route "resource-server" (for logging/metrics)
                .GET("/demo", http()) // match GET /demo requests and proxy them via the generic HTTP handler
                .before(uri("http://localhost:8081")) // rewrite the target URI to the downstream resource-server host
                .filter(tokenRelay()) // attach the logged-in user's OAuth2 access token as a Bearer header
                .build(); // assemble the configured route into a RouterFunction bean
    }
}
