// ============================================
// 3. CreditNoteService.java
// ============================================
package com.example.invoice_lookup.service;

import com.example.invoice_lookup.model.CreditUpload;
import com.example.invoice_lookup.repository.CreditUploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditNoteService {

    private final CreditUploadRepository creditUploadRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreditNoteService.class);

    public CreditNoteService(CreditUploadRepository creditUploadRepository) {
        this.creditUploadRepository = creditUploadRepository;
    }

    public List<CreditUpload> getAllCreditNotes(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        logger.info("Fetching all credit notes from {} to {}", startDate, endDate);
        return creditUploadRepository.findAllByDateRange(startDate, endDate);
    }

    public List<CreditUpload> getSuccessfulCreditNotes(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        logger.info("Fetching successful credit notes from {} to {}", startDate, endDate);
        return creditUploadRepository.findSuccessfulCreditNotesByDateRange(startDate, endDate);
    }

    public List<CreditUpload> getPendingCreditNotes(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        logger.info("Fetching pending credit notes from {} to {}", startDate, endDate);
        return creditUploadRepository.findPendingCreditNotesByDateRange(startDate, endDate);
    }
}
