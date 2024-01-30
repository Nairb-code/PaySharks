package com.bripay.oauthservice.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class CustomGrantedAuthoritiesMapper implements GrantedAuthoritiesMapper {
    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> mappedAuthorities = new HashSet<>();

        authorities.forEach(authority -> {
            if (OidcUserAuthority.class.isInstance(authority)) {
                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

            } else if (OAuth2UserAuthority.class.isInstance(authority)) {
                OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority) authority;
                Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();
                // Aquí puedes mapear los atributos del usuario obtenidos de userAttributes a roles y permisos
                // y agregarlos a mappedAuthorities según tus necesidades.
                String role = "USER"; // Asigna el rol que corresponda según los atributos del usuario
                mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }
        });

        return mappedAuthorities;
    }
}

