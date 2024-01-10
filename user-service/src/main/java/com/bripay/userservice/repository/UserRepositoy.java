package com.bripay.userservice.repository;

import com.bripay.commonsservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepositoy extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    // Método personalizado para verificar si existe otro usuario con el mismo nombre de usuario, excluyendo el usuario actual
    boolean existsByUsernameAndIdNot(String username, Long userId);
    boolean existsByEmailAndIdNot(String email, Long userId);
}
