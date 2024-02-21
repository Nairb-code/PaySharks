package com.bripay.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http){
        http
                .authorizeExchange()
                .pathMatchers("/authorized", "/login/oauth2/code/react-app", "/api/oauth-service/**", "/api/oauth/**", "/api/oauthClient/**", "/api/users/api/v1/users/{id}").permitAll()
                .pathMatchers(HttpMethod.GET, "/api/users/api/v1/users").hasAnyAuthority("SCOPE_ADMIN", "ROLE_ADMIN", "ADMIN")
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
                .and()
                .oauth2ResourceServer()
                .jwt();

        http.csrf().disable();

        return http.build();
    }

}
