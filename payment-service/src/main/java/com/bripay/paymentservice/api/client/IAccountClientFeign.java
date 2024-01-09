package com.bripay.paymentservice.api.client;

import com.bripay.commonsservice.dto.AccountDto;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "account-service", url = "localhost:8081")
public interface IAccountClientFeign {

    /** Obtener Account por Id. **/
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> findById(@PathVariable Long id);

    /** Obtener la Cuenta por el numero de cuenta.  **/
    @GetMapping("/api/v1/accounts/account/{numberAccount}")
    public ResponseEntity<AccountDto> findByNumberAccount(@PathVariable String numberAccount);

    /** Obtener todas las Cuentas Dto.  **/
    @GetMapping
    public ResponseEntity<List<AccountDto>> findAllUserDto();

    /** Obtener todlas cuentas por Username**/
    @GetMapping("/api/v1/accounts/username/{username}")
    public ResponseEntity<List<AccountDto>> findAllByUsername(@PathVariable String username);


    /** Registrando una nueva Cuenta.   **/
    @PostMapping("/api/v1/accounts")
    public ResponseEntity<AccountDto> save(@Valid @RequestBody AccountDto accountDto);

    /** Actualizar datos de una Cuenta.  **/
    @PutMapping("/api/v1/accounts")
    public ResponseEntity<AccountDto> update(@Valid @RequestBody AccountDto accountDto);

    /** Eliminar una Cuenta por Id. **/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id);
}
