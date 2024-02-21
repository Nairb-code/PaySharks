package com.bripay.oauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/home", "/authorized").permitAll()
                .requestMatchers("/api/v1/home/protegido", "http://localhost:8082/api/v1/users").hasAnyAuthority("SCOPE_USER")
                .requestMatchers("/api/v1/home/admin").hasAnyAuthority("SCOPE_read", "SCOPE_write", "SCOPE_ADMIN")
                .anyRequest().authenticated()
                .and()
                .sessionManagement((session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)))
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .oauth2Login(login -> login.loginPage("/oauth2/authorization/react-app"))
                .oauth2Client(withDefaults()).csrf( csrf -> csrf.disable())
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(withDefaults()))
                .build();
    }
}