package com.bripay.paymentservice.service;


import com.bripay.commonsservice.dto.PaymentDto;

import java.util.List;

public interface IPaymentService {
    /**
     * Consultas
     **/
    PaymentDto findById(Long id);
    List<PaymentDto> findAll();

    /** Registro    **/
    PaymentDto save(PaymentDto paymentDto);

    /** Actualizar  **/
    PaymentDto update(PaymentDto paymentDto);

    /** Eliminar    **/
    void deleteById(Long id);
}
