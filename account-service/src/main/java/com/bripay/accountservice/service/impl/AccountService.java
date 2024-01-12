package com.bripay.accountservice.service.impl;

import com.bripay.accountservice.api.client.IUserClientFeign;
import com.bripay.accountservice.repository.AccountRepository;
import com.bripay.accountservice.service.IAccountService;
import com.bripay.commonsservice.dto.AccountDto;
import com.bripay.commonsservice.dto.UserDto;
import com.bripay.commonsservice.entity.AccountEntity;
import com.bripay.commonsservice.exception.DuplicateResourceException;
import com.bripay.commonsservice.exception.ResourceNotFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AccountService implements IAccountService{
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    IUserClientFeign userClientFeign;
    @Autowired
    ObjectMapper mapper;

    /**
     * Sección de Consultas.
     **/
    @Override
    public AccountDto findById(Long id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The account with ID = " + id + " is not registered.")
        );

        return mapper.convertValue(accountEntity, AccountDto.class);
    }

    @Override
    public AccountDto findByNumberAccount(String numberAccount) {
        AccountEntity accountEntity = accountRepository.findByNumberAccount(numberAccount).orElseThrow(
                () -> new ResourceNotFoundException("The account '" + numberAccount + "' is not registered.")
        );

        return mapper.convertValue(accountEntity, AccountDto.class);
    }

    @Override
    public List<AccountDto> findAllAccountDto() {
        List<AccountEntity> listAccountEntity = accountRepository.findAll();
        List<AccountDto> listAccountDto = new ArrayList<>();

        listAccountEntity.forEach(accountEntity -> {
            listAccountDto.add(mapper.convertValue(accountEntity, AccountDto.class));
        });

        return listAccountDto;
    }

    @Override
    public List<AccountDto> findAllAccountByUsername(String username) {
        List<AccountEntity> listAccountEntity = accountRepository.findAllByUsername(username);

        if (listAccountEntity.isEmpty()){
            throw new ResourceNotFoundException("The username '" + username + "' is not registered.");
        }

        List<AccountDto> listAccountDto = new ArrayList<>();

        listAccountEntity.forEach(accountEntity -> {
            listAccountDto.add(mapper.convertValue(accountEntity, AccountDto.class));
        });

        return listAccountDto;
    }

    /** Sección de Registro.    **/
    @Override
    public AccountDto save(AccountDto accountDto) {
        Optional<AccountEntity> existAccountEntity = accountRepository.findByNumberAccount(accountDto.getNumberAccount());

        existAccountEntity.ifPresent( x -> {
            throw new ResourceNotFoundException("The account with number account = " + accountDto.getNumberAccount() + " is registered.");
        });

        // Validar que el username existe
        userClientFeign.findByUsername(accountDto.getUsername());

        AccountEntity accountEntity = mapper.convertValue(accountDto, AccountEntity.class);

        return mapper.convertValue(accountRepository.save(accountEntity), AccountDto.class);
    }

    /** Sección de Actualización.   **/
    @Override
    public AccountDto update(AccountDto accountDto) {
        // Verificar si la cuenta existe
        AccountEntity existingAccountEntity = accountRepository.findById(accountDto.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account with ID " + accountDto.getId() + " not found"));

        // Validar que el username existe
        userClientFeign.findByUsername(accountDto.getUsername());

        // Verificar si el nuevo número de cuenta ya está registrado para otro usuario (excluyendo al usuario actual)
        if (accountRepository.existsByNumberAccountAndIdNot(accountDto.getNumberAccount(), accountDto.getId())) {
            throw new DuplicateResourceException("The number account " + accountDto.getNumberAccount() + " is already registered");
        }

        // Actualizar los campos de la cuenta existente con los valores proporcionados en el AccountDto
        existingAccountEntity.setNumberAccount(accountDto.getNumberAccount());
        existingAccountEntity.setCashAvailable(accountDto.getCashAvailable());
        existingAccountEntity.setUsername(accountDto.getUsername());

        return mapper.convertValue(accountRepository.save(existingAccountEntity), AccountDto.class);
    }

    /** Sección de Eliminación. **/
    @Override
    public void deleteById(Long id) {
        AccountEntity accountEntity = accountRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("The account with ID = " + id + " is not registered.")
        );

        accountRepository.deleteById(id);
    }
}
