package com.bripay.commonsservice.dto;

import com.bripay.commonsservice.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class PaymentDto implements Serializable {
    private Long id;

    @Column(length = 6 ,nullable = false)
    @Size(min = 6, max = 6, message = "La cuenta debe contener 6 digitos")
    @NotBlank(message = "The account can't be blank")
    @Pattern(regexp = "0*[1-9][0-9]*", message = "La cuenta debe contener solo caracteres numéricos y puede empezar con ceros")
    private String senderAccount;

    @Column(length = 6 ,nullable = false)
    @Size(min = 6, max = 6, message = "La cuenta debe contener 6 digitos")
    @NotBlank(message = "The account can't be blank")
    @Pattern(regexp = "0*[1-9][0-9]*", message = "La cuenta debe contener solo caracteres numéricos y puede empezar con ceros")
    private String beneficiaryAccount;

    @Column(nullable = false)
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor que cero")
    private double amount;

    @Column(nullable = false)
    @NotNull(message = "The payment method can't be empty")
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    //@Pattern(regexp = "\\d{8} \\d{2}:\\d{2}:\\d{2}", message = "Invalid date format. Use 'ddMMyyyy HH:mm:ss'")
    @JsonFormat(pattern = "ddMMyyyy HH:mm:ss")
    private Date paymentDate;
}
