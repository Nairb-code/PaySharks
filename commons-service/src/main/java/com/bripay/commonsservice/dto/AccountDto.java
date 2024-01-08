package com.bripay.commonsservice.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class AccountDto implements Serializable {
    private Long id;

    @Column(length = 10 ,nullable = false, unique = true)
    @Size(min = 6, max = 6, message = "La cuenta debe contener 6 digitos")
    @NotBlank(message = "The account can't be blank")
    @Pattern(regexp = "0*[1-9][0-9]*", message = "La cuenta debe contener solo caracteres numéricos y puede empezar con ceros")
    private String numberAccount;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    private double cashAvailable;

    @Column(length = 15, nullable = false)
    @Size(min = 3, max = 15, message = "The username should have between {min} to {max} characters")
    @NotBlank(message = "The username can't be blank")
    private String username;
}
