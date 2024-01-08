package com.bripay.userservice.service;

import com.bripay.commonsservice.dto.UserDto;

import java.util.List;

public interface IUserService {
    /** Consultas   **/
    UserDto findById(Long id);
    UserDto findByUsername(String username);
    List<UserDto> findAllUserDto();

    /** Registro    **/
    UserDto save(UserDto userDto);

    /** Actualización   **/
    UserDto update(UserDto userDto);

    /** Eliminación **/
    void deleteById(Long id);
}
