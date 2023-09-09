package com.picpaysimplificado2.controllers;

import com.picpaysimplificado2.dtos.TransactionDTO;
import com.picpaysimplificado2.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<?> createTransaction(@RequestBody TransactionDTO transaction){
        System.out.println("TRANSACTION:"+transaction);
        TransactionDTO dto = this.transactionService.createTransaction(transaction);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
    @GetMapping
    public ResponseEntity<?> listTransactions(){
        return ResponseEntity.ok().body(this.transactionService.listAllTransactions());
    }
}
