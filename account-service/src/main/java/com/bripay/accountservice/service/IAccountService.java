package com.bripay.accountservice.service;

import com.bripay.commonsservice.dto.AccountDto;
import com.bripay.commonsservice.entity.AccountEntity;

import java.util.List;

public interface IAccountService {
    /**
     * Consultas
     **/
    AccountDto findById(Long id);
    AccountDto findByNumberAccount(String numberAcount);
    List<AccountDto> findAllAccountDto();
    List<AccountDto> findAllAccountByUsername(String username);

    /** Registro    **/
    AccountDto save(AccountDto accountDto);

    /** Actualizar  **/
    AccountDto update(AccountDto accountDto);

    /** Eliminar    **/
    void deleteById(Long id);

}
