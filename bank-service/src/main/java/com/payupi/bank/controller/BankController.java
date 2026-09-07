package com.payupi.bank.controller;


import com.payupi.bank.dto.BankResponse;
import com.payupi.bank.entity.Banks;
import com.payupi.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bank")
@RequiredArgsConstructor
@Slf4j
//@CrossOrigin(origins = "http://localhost:4200")
public class BankController{

     private final BankService bankService;

     @GetMapping("/getAllBanks")
    public ResponseEntity<List<BankResponse>> getAllBanks(){

          log.info("getting all banks  vishvjeet");

          List<BankResponse> banks = bankService.getAllBanks();

          return ResponseEntity.ok(banks);
     }



}

