package com.bripay.paymentservice.web;

import com.bripay.commonsservice.dto.PaymentDto;
import com.bripay.paymentservice.service.IPaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    IPaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.findById(id));
    }
    @GetMapping("/senderAccount/{senderAccount}")
    public ResponseEntity<List<PaymentDto>> findAllBySenderAccount(@PathVariable String senderAccount){
        return ResponseEntity.ok(paymentService.findAllBySenderAccount(senderAccount));
    }

    @GetMapping
    public ResponseEntity<List<PaymentDto>> findAll(){
        return ResponseEntity.ok(paymentService.findAll());
    }

    @GetMapping("/findByDateRange")
    public ResponseEntity<List<PaymentDto>> findPaymentsByDateRange(
            @RequestParam @DateTimeFormat(pattern = "ddMMyyyy") Date fromDate,
            @RequestParam @DateTimeFormat(pattern = "ddMMyyyy") Date toDate) {
        List<PaymentDto> payments = paymentService.findPaymentByDateRange(fromDate, toDate);
        return ResponseEntity.ok(payments);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> save(@Valid @RequestBody PaymentDto paymentDto){
        return ResponseEntity.ok(paymentService.save(paymentDto));
    }
}
