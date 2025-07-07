package com.example.invoice_lookup.service;





import com.example.invoice_lookup.model.InvoiceUpload;
import com.example.invoice_lookup.repository.InvoiceUploadRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InvoiceUploadService {

    private final InvoiceUploadRepository repository;

    public InvoiceUploadService(InvoiceUploadRepository repository) {
        this.repository = repository;
    }

    public Optional<InvoiceUpload> getByDebitNo(Integer debitNo) {
        return repository.findByDebitNo(debitNo);
    }

    public void resetTransaction(Integer debitNo) {
        Optional<InvoiceUpload> recordOpt = repository.findByDebitNo(debitNo);
        recordOpt.ifPresent(record -> {
            record.setProcessStatus(0);
            record.setUpdateComments(null);
            repository.save(record);
        });
    }
}
