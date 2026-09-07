package com.payupi.bank.repository;

import com.payupi.bank.dto.BankResponse;
import com.payupi.bank.entity.Banks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BankRepository extends JpaRepository<Banks,Long> {

    @Query("""
       SELECT new com.upi.bank.dto.BankResponse(
           b.id,
           b.bankName
       )
       FROM Banks b
       """)
    List<BankResponse> findAllBanks();


}
