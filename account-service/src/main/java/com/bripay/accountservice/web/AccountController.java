package com.bripay.accountservice.web;

import com.bripay.accountservice.service.IAccountService;
import com.bripay.commonsservice.dto.AccountDto;
import com.bripay.commonsservice.entity.AccountEntity;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    @Autowired
    IAccountService accountService;

    /** Obtener Account por Id. **/
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(accountService.findById(id));
    }

    /** Obtener todas las Cuentas Dto.  **/
    @GetMapping
    public ResponseEntity<List<AccountDto>> findAllUserDto(){
        return ResponseEntity.ok(accountService.findAllAccountDto());
    }

    /** Obtener todlas cuentas por Username**/
    @GetMapping("/username/{username}")
    public ResponseEntity<List<AccountDto>> findAllByUsername(@PathVariable String username){
        return ResponseEntity.ok(accountService.findAllAccountByUsername(username));
    }


    /** Registrando una nueva Cuenta.   **/
    @PostMapping
    public ResponseEntity<AccountDto> save(@Valid @RequestBody AccountDto accountDto){
        return ResponseEntity.ok(accountService.save(accountDto));
    }

    /** Actualizar datos de una Cuenta.  **/
    @PutMapping
    public ResponseEntity<AccountDto> update(@Valid @RequestBody AccountDto accountDto){
        return ResponseEntity.ok(accountService.update(accountDto));
    }

    /** Eliminar una Cuenta por Id. **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        accountService.deleteById(id);

        return ResponseEntity.ok().build();
    }
}
