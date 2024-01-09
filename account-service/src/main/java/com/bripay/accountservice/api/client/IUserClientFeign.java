package com.bripay.accountservice.api.client;

import com.bripay.commonsservice.dto.AccountDto;
import com.bripay.commonsservice.dto.UserDto;
import com.bripay.commonsservice.entity.AccountEntity;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "user-service", url = "localhost:8082")
public interface IUserClientFeign {
    /** Obtener Usuario por Id. **/
    @GetMapping("/api/v1/users/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id);

    /** Obtener Usuario por Username. **/
    @GetMapping("/api/v1/users/username/{username}")
        public ResponseEntity<UserDto> findByUsername(@PathVariable String username); //modificar responseEntity por Optional.

    /** Obtener todas los Usuarios.  **/
    @GetMapping("/api/v1/users")
    public ResponseEntity<List<UserDto>> findAllUserDto();

    /** Registrando una nuevo Usuario.   **/
    @PostMapping("/api/v1/users")
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto);

    /** Actualizar datos de un Usuario.  **/
    @PutMapping("/api/v1/users")
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto);

    /** Eliminar una Usuario por Id. **/
    @DeleteMapping("/api/v1/users/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id);
}
