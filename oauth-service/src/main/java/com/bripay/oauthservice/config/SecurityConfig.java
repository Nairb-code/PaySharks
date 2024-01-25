package com.bripay.oauthservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /** Cargar detalles del usuario en la memoria, utilizando InMemoryUserDetailsManager**/
    @Bean
    public UserDetailsService userDetailsService() {
        // Recodifica la contraseña del usuario admin
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        UserDetails user = User.withUsername("user")
                .password(encoder.encode("1234"))
                .roles("USER")
                .build();


        UserDetails admin = User.withUsername("admin")
                .password(encoder.encode("admin"))
                .roles("ADMIN")
                .build();

        // Imprimir contraseñas en consola
        System.out.println("Password for user: " + user.getPassword());
        System.out.println("Password for admin: " + admin.getPassword());

        return new InMemoryUserDetailsManager(user, admin);
    }

    /** IMplementando filtro de seguridad. Autenticación y autorización.   **/

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/home").permitAll()
                .requestMatchers("/api/v1/home/protegido").hasRole("USER")
                .requestMatchers("/api/v1/home/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll()
                .and()
                .httpBasic()
                .and()
                .oauth2Login(Customizer.withDefaults())
                .build();
    }
}
