package com.bripay.oauthserver.service;

import com.bripay.commonsservice.dto.UserDto;
import com.bripay.oauthserver.api.IUserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService, IUserService{
    @Autowired
    private IUserFeignClient userFeignClient;

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = userFeignClient.findByUsername(username).getBody();

        // verificar si el usuario existe, sino lanzar excepción.

        List<GrantedAuthority> authorities = userDto.getRoles()
                .stream()
                .map( role -> new SimpleGrantedAuthority(role.getNombre()))
                .peek(authoritie -> logger.info("Role: " + authoritie.getAuthority()))
                .collect(Collectors.toList());

        return new User(userDto.getUsername(), "{noop}" + userDto.getPassword(), true, true, true, true, authorities);
    }

    @Override
    public UserDto findByUsername(String username) {
        return userFeignClient.findByUsername(username).getBody();
    }
}
