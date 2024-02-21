package com.bripay.commonsservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable( name = "usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"usuario_id", "role_id"})}
    )
    private List<RoleEntity> roles;

}
