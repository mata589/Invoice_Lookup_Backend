package com.example.invoice_lookup.repository;


import com.example.invoice_lookup.model.InvoiceUpload;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InvoiceUploadRepository extends JpaRepository<InvoiceUpload, Long> {
    Optional<InvoiceUpload> findByDebitNo(Integer debitNo);
}