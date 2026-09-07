package com.payupi.bank.service.impl;

import com.payupi.bank.dto.BankResponse;
import com.payupi.bank.entity.Banks;
import com.payupi.bank.repository.BankRepository;
import com.payupi.bank.service.BankService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

     private final BankRepository bankRepository;

        @Override
        public List<BankResponse> getAllBanks()
        {
            return  bankRepository.findAllBanks();
        }


}