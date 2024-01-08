package com.bripay.userservice.service.impl;

import com.bripay.commonsservice.dto.UserDto;
import com.bripay.commonsservice.entity.UserEntity;
import com.bripay.commonsservice.exception.DuplicateResourceException;
import com.bripay.commonsservice.exception.ResourceNotFoundException;
import com.bripay.userservice.repository.UserRepositoy;
import com.bripay.userservice.service.IUserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    UserRepositoy userRepositoy;
    @Autowired
    ObjectMapper mapper;

    /** Sección de consultas.   **/
    @Override
    public UserDto findById(Long id) {
        UserEntity userEntity = userRepositoy.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The user with ID = " + id + " is not registered.")
        );

        return mapper.convertValue(userEntity, UserDto.class);
    }

    @Override
    public UserDto findByUsername(String username) {
        UserEntity userEntity = userRepositoy.findByUsername(username);

        if (userEntity == null) {
            throw new ResourceNotFoundException("The username " + username + "is not registered.");
        }

        return mapper.convertValue(userEntity, UserDto.class);
    }

    @Override
    public List<UserDto> findAllUserDto() {
        List<UserEntity> listUserEntity = userRepositoy.findAll();
        List<UserDto> listUserDto = new ArrayList<>();

        listUserEntity.forEach(userEntity -> {
            listUserDto.add(mapper.convertValue(userEntity, UserDto.class));
        });

        return listUserDto;
    }

    /** Sección de Registro **/
    @Override
    public UserDto save(UserDto userDto) {
        // Verificar si el username ya está registrado
        if (userRepositoy.existsByUsername(userDto.getUsername())) {
            throw new DuplicateResourceException("The username " + userDto.getUsername() + " is already registered");
        }

        // Verificar si el correo electrónico ya está registrado
        if (userRepositoy.existsByEmail(userDto.getEmail())) {
            throw new DuplicateResourceException("The email " + userDto.getEmail() + " is already registered");
        }

        UserEntity  userEntity = mapper.convertValue(userDto, UserEntity.class);

        userEntity = userRepositoy.save(userEntity);

        return mapper.convertValue(userEntity, UserDto.class);
    }

    /** Sección de Actualización **/
    @Override
    public UserDto update(UserDto userDto) {
        // Verificar si el usuario existe
        UserEntity existingUserEntity = userRepositoy.findById(userDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userDto.getId() + " not found"));

        // Verificar si el nuevo nombre de usuario ya está registrado para otro usuario (excluyendo al usuario actual)
        if (userRepositoy.existsByUsernameAndIdNot(userDto.getUsername(), userDto.getId())) {
            throw new DuplicateResourceException("The username " + userDto.getUsername() + " is already registered");
        }

        // Verificar si el nuevo correo electrónico ya está registrado para otro usuario (excluyendo al usuario actual)
        if (userRepositoy.existsByEmailAndIdNot(userDto.getEmail(), userDto.getId())) {
            throw new DuplicateResourceException("The email " + userDto.getEmail() + " is already registered");
        }

        // Actualizar los campos del usuario existente con los valores proporcionados en el UserDto
        existingUserEntity.setUsername(userDto.getUsername());
        existingUserEntity.setEmail(userDto.getEmail());
        existingUserEntity.setPassword(userDto.getPassword());
        existingUserEntity.setAvailable(userDto.isAvailable());

        // Guardar el usuario actualizado en el repositorio
        existingUserEntity = userRepositoy.save(existingUserEntity);

        return mapper.convertValue(existingUserEntity, UserDto.class);
    }

    /** Sección de Eliminación  **/
    @Override
    public void deleteById(Long id) {
        UserEntity userEntity = userRepositoy.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The user with ID = " + id + " is not registered.")
        );

        userRepositoy.deleteById(id);
    }
}
