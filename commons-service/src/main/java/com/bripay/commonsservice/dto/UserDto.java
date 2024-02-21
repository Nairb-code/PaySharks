package com.bripay.commonsservice.dto;

import com.bripay.commonsservice.entity.RoleEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserDto implements Serializable {
    private Long id;
    @Column(length = 15, nullable = false, unique = true)
    @Size(min = 3, max = 15, message = "The username should have between {min} to {max} characters")
    @NotBlank(message = "The username can't be blank")
    private String username;

    @Column(length = 40, nullable = false, unique = true)
    @Size(min = 10, max = 40, message = "The email should have between {min} to {max} characters")
    @NotBlank(message = "The email can't be blank")
    @Email(message = "The email format is not valid")
    private String email;

    @Column(length = 15, nullable = false)
    @Size(min = 5, max = 15, message = "The password should have between {min} to {max} characters")
    @NotBlank(message = "The password can't be blank")
    private String password;

    @Column(columnDefinition = "TINYINT", nullable = false)
    private boolean available;

    private List<RoleEntity> roles;
}
