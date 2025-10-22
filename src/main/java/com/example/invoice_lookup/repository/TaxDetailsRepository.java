package com.example.invoice_lookup.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;



import com.example.invoice_lookup.model.TaxDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaxDetailsRepository extends JpaRepository<TaxDetails, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM TaxDetails t WHERE t.debitNo IN :debitNos")
    int deleteByDebitNos(@Param("debitNos") List<Integer> debitNos);


}
