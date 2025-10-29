// ============================================
// DebitNoteService.java - NEW SERVICE FILE
// ============================================
package com.example.invoice_lookup.service;

import com.example.invoice_lookup.model.DebitNote;
import com.example.invoice_lookup.model.InvoiceUpload;
import com.example.invoice_lookup.repository.InvoiceUploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class DebitNoteService {

    private final InvoiceUploadRepository invoiceUploadRepository;
    private static final Logger logger = LoggerFactory.getLogger(DebitNoteService.class);

    public DebitNoteService(InvoiceUploadRepository invoiceUploadRepository) {
        this.invoiceUploadRepository = invoiceUploadRepository;
    }

    /**
     * Get all debit notes within a date range
     */
    public List<DebitNote> getAllDebitNotes(LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching all debit notes from {} to {}", startDate, endDate);
        List<DebitNote> debitNotes = invoiceUploadRepository.findAllByDateRange(startDate, endDate);
        logger.info("Found {} debit notes", debitNotes.size());
        return debitNotes;
    }

    /**
     * Get successful debit notes (UpdateStatus = 1) within a date range
     */
    public List<DebitNote> getSuccessfulDebitNotes(LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching successful debit notes from {} to {}", startDate, endDate);
        List<DebitNote> debitNotes = invoiceUploadRepository.findSuccessfulDebitNotesByDateRange(startDate, endDate);
        logger.info("Found {} successful debit notes", debitNotes.size());
        return debitNotes;
    }

    /**
     * Get pending debit notes (UpdateStatus != 1 or NULL) within a date range
     */
    public List<DebitNote> getPendingDebitNotes(LocalDate startDate, LocalDate endDate) {
        logger.info("Fetching pending debit notes from {} to {}", startDate, endDate);
        List<DebitNote> debitNotes = invoiceUploadRepository.findPendingDebitNotesByDateRange(startDate, endDate);
        logger.info("Found {} pending debit notes", debitNotes.size());
        return debitNotes;
    }

    /**
     * Get a specific debit note by its debitNo
     */
    public DebitNote getDebitNoteByDebitNote(Integer debitNo) {
        logger.info("Fetching debit note with debitNo: {}", debitNo);
        DebitNote debitNote = invoiceUploadRepository.findByDebitNote(debitNo);
        if (debitNote != null) {
            logger.info("Found debit note with debitNo: {}", debitNo);
        } else {
            logger.warn("No debit note found with debitNo: {}", debitNo);
        }
        return debitNote;
    }

    // ============================================
    // NEW METHODS: Update Email & TIN
    // ============================================

    /**
     * Update the buyer email for a debit note identified by debitNo
     *
     * @param debitNo  the debit note number
     * @param newEmail the new email address
     * @return true if updated successfully, false otherwise
     */
    @Transactional
    public boolean updateBuyerEmailByDebitNo(Integer debitNo, String newEmail) {
        if (debitNo == null || newEmail == null || newEmail.trim().isEmpty()) {
            logger.warn("Invalid input: debitNo or newEmail is null/empty");
            return false;
        }

        logger.info("Attempting to update buyer email for debitNo: {} to {}", debitNo, newEmail);
        int updatedRows = invoiceUploadRepository.updateBuyerEmailByDebitNo(debitNo, newEmail.trim());

        if (updatedRows > 0) {
            logger.info("Successfully updated buyer email for debitNo: {}", debitNo);
            return true;
        } else {
            logger.warn("No record found or email unchanged for debitNo: {}", debitNo);
            return false;
        }
    }

    /**
     * Update the buyer TIN for a debit note identified by debitNo
     *
     * @param debitNo the debit note number
     * @param newTin  the new TIN number
     * @return true if updated successfully, false otherwise
     */
    @Transactional
    public boolean updateBuyerTinByDebitNo(Integer debitNo, String newTin) {
        if (debitNo == null || newTin == null || newTin.trim().isEmpty()) {
            logger.warn("Invalid input: debitNo or newTin is null/empty");
            return false;
        }

        logger.info("Attempting to update buyer TIN for debitNo: {} to {}", debitNo, newTin);
        int updatedRows = invoiceUploadRepository.updateBuyerTinByDebitNo(debitNo, newTin.trim());

        if (updatedRows > 0) {
            logger.info("Successfully updated buyer TIN for debitNo: {}", debitNo);
            return true;
        } else {
            logger.warn("No record found or TIN unchanged for debitNo: {}", debitNo);
            return false;
        }
    }
}

