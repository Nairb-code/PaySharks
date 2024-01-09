package com.bripay.paymentservice.service.impl;

import com.bripay.commonsservice.dto.AccountDto;
import com.bripay.commonsservice.dto.PaymentDto;
import com.bripay.commonsservice.entity.PaymentEntity;
import com.bripay.commonsservice.exception.ResourceNotFoundException;
import com.bripay.paymentservice.api.client.IAccountClientFeign;
import com.bripay.paymentservice.repository.PaymentRepository;
import com.bripay.paymentservice.service.IPaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentService implements IPaymentService {
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    IAccountClientFeign accountClientFeign;
    @Autowired
    ObjectMapper mapper;

    @Override
    public PaymentDto findById(Long id) {
        PaymentEntity paymentEntity = paymentRepository.findById(id).orElseThrow(() ->
             new ResourceNotFoundException("Payment with ID = " + id + " is not registered.")
        );

        return mapper.convertValue(paymentEntity, PaymentDto.class);
    }

    @Override
    public List<PaymentDto> findAll() {
        List<PaymentEntity> listPaymentEntity = paymentRepository.findAll();

        return listPaymentEntity.stream().map(paymentEntity ->
                mapper.convertValue(paymentEntity, PaymentDto.class)
        ).collect(Collectors.toList());
    }

    @Override
    public PaymentDto save(PaymentDto paymentDto) {

        // 1. Consultar si existe la cuenta beneficiaria, sino arrojar Excepcion.
        ResponseEntity<AccountDto> beneficiaryAccount = accountClientFeign.findByNumberAccount(paymentDto.getBeneficiaryAccount());
        if (beneficiaryAccount.getStatusCode() != HttpStatus.OK || beneficiaryAccount.getBody() == null){
            throw new ResourceNotFoundException("Account '" + paymentDto.getBeneficiaryAccount() + "' is not registered.");
        }

        // 2. Consultar si existe la cuenta remitente, sino arrojar Excepcicion.
        ResponseEntity<AccountDto> senderAccount = accountClientFeign.findByNumberAccount(paymentDto.getSenderAccount());
        if (senderAccount.getStatusCode() != HttpStatus.OK || senderAccount.getBody() == null){
            throw new ResourceNotFoundException("Account '" + paymentDto.getSenderAccount()+ "' is not registered.");
        }



        double senderCashAvailable = senderAccount.getBody().getCashAvailable() - paymentDto.getAmount();

        if (senderCashAvailable <= 0){
            throw new RuntimeException("Payment is not possible. You contain $" + senderCashAvailable);
        }

        double beneficiaryCashAvailable = beneficiaryAccount.getBody().getCashAvailable() + paymentDto.getAmount();

        senderAccount.getBody().setCashAvailable(senderCashAvailable);
        beneficiaryAccount.getBody().setCashAvailable(beneficiaryCashAvailable);

        accountClientFeign.update(senderAccount.getBody());
        accountClientFeign.update(beneficiaryAccount.getBody());

        // 3. Convertir paymentDto a PaymentEntity
        PaymentEntity paymentEntity = mapper.convertValue(paymentDto, PaymentEntity.class);

        // 4. Convertir PaymentEntity a PaymentDto
        return mapper.convertValue(paymentRepository.save(paymentEntity), PaymentDto.class);
    }

    @Override
    public PaymentDto update(PaymentDto paymentDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        PaymentEntity paymentEntity = paymentRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Payment with ID = " + id + " is not registered.")
        );

        paymentRepository.deleteById(id);
    }
}
