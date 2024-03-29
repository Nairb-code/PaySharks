package com.bripay.paymentservice.service.impl;

import com.bripay.commonsservice.dto.AccountDto;
import com.bripay.commonsservice.dto.PaymentDto;
import com.bripay.commonsservice.entity.PaymentEntity;
import com.bripay.commonsservice.enums.PaymentMethod;
import com.bripay.commonsservice.exception.NullOrEmptyFieldException;
import com.bripay.commonsservice.exception.ResourceNotFoundException;
import com.bripay.paymentservice.api.client.IAccountClientFeign;
import com.bripay.paymentservice.repository.PaymentRepository;
import com.bripay.paymentservice.service.IPaymentService;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
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
    public List<PaymentDto> findAllBySenderAccount(String senderAccount) {
        boolean existsSenderAccount = paymentRepository.existsBySenderAccount(senderAccount);
        boolean existsBeneficiaryAccount = paymentRepository.existsByBeneficiaryAccount(senderAccount);

        if (!existsSenderAccount){
            if (!existsBeneficiaryAccount){
                throw new ResourceNotFoundException("The account " + senderAccount + " is not registered.");
            }
            throw new RuntimeException("There are no payments registered with the account '" + senderAccount+  "'");
        }

        List<PaymentEntity> listAccountEntity = paymentRepository.findAllBySenderAccount(senderAccount);

        return listAccountEntity.stream()
                .map(accountEntity -> mapper.convertValue(accountEntity, PaymentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> findAllByPaymentMethod(PaymentMethod paymentMethod) {
        List<PaymentEntity> listPaymentEntity = paymentRepository.findAllByPaymentMethod(paymentMethod);


        return listPaymentEntity.stream()
                .map( paymentEntity -> mapper.convertValue(paymentEntity, PaymentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentDto> findPaymentByDateRange(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null){
            throw new NullOrEmptyFieldException("The Dates [ fromDate - toDate ] to consult can't be Null o Empty");
        }

        if (fromDate.compareTo(toDate) > 0){
            throw new IllegalArgumentException("The field fromDate must be before toDate");
        }

        // Incrementar la fecha toDate en un día para incluir todos los pagos hasta el final de toDate
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(toDate);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        toDate = calendar.getTime();

        // Buscar pagos por rango de fechas
        List<PaymentEntity> paymentEntities = paymentRepository.findByPaymentDateBetween(fromDate, toDate);

        return paymentEntities
                .stream()
                .map(paymentEntity -> mapper.convertValue(paymentEntity, PaymentDto.class))
                .collect(Collectors.toList());
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

        //paymentDto.setPaymentDate(new Date());

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
