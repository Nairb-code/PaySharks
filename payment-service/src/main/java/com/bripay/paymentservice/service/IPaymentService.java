package com.bripay.paymentservice.service;


import com.bripay.commonsservice.dto.PaymentDto;

import java.util.Date;
import java.util.List;

public interface IPaymentService {
    /**
     * Consultas
     **/
    PaymentDto findById(Long id);
    List<PaymentDto> findAll();
    List<PaymentDto> findAllBySenderAccount(String senderAccount);
    List<PaymentDto> findPaymentByDateRange(Date fromDate, Date toDate);

    /** Registro    **/
    PaymentDto save(PaymentDto paymentDto);

    /** Actualizar  **/
    PaymentDto update(PaymentDto paymentDto);

    /** Eliminar    **/
    void deleteById(Long id);
}
