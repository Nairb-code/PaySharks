package com.bripay.userservice.web;


import com.bripay.commonsservice.dto.UserDto;
import com.bripay.userservice.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RefreshScope
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    IUserService userService;

    /** Obtener Usuario por Id**/
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    /** Obtener Usuario por Username    **/
    @GetMapping("/username/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username){
        return ResponseEntity.ok(userService.findByUsername(username));
    }

    /** Obtener todos los Usuarios Dto**/
    @GetMapping
    public ResponseEntity<List<UserDto>> findAllUserDto(){
        return ResponseEntity.ok(userService.findAllUserDto());
    }

    /** Registrando un nuevo Usuario **/
    @PostMapping
    public ResponseEntity<UserDto> save(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.save(userDto));
    }
    // modificar el codgio de respuesta
    /** Actualizar Usuario **/
    @PutMapping
    public ResponseEntity<UserDto> update(@Valid @RequestBody UserDto userDto){
        return ResponseEntity.ok(userService.update(userDto));
    }

    /** Eliminar Usuario por Id **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
