package com.bripay.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig{
    @Bean
    public SecurityWebFilterChain configure(ServerHttpSecurity http){
        http
                .authorizeExchange()
                .pathMatchers("/authorized", "/api/oauth/**").permitAll()
                .pathMatchers( "/api/users/api/v1/users/**", "/api/users/api/v1/accounts/**", "/api/payment/api/v1/payment/**").hasAnyAuthority("SCOPE_ADMIN")
                .pathMatchers( "/api/users/api/v1/users/{id}", "/api/users/api/v1/accounts/{id}", "/api/payment/api/v1/payment/{id}").hasAnyAuthority("SCOPE_USER")
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
