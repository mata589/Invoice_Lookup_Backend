package com.example.invoice_lookup.repository;

import com.example.invoice_lookup.model.DebitNote;
import com.example.invoice_lookup.model.InvoiceUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface InvoiceUploadRepository extends JpaRepository<InvoiceUpload, Long> {
    Optional<InvoiceUpload> findByDebitNo(Integer debitNo);

    @Modifying
    @Transactional
    @Query("DELETE FROM InvoiceUpload i WHERE i.debitNo IN (:debitNos)")
    void deleteByDebitNos(List<Integer> debitNos);

    @Query("SELECT i.buyerTin FROM InvoiceUpload i WHERE i.debitNo = :debitNo")
    Optional<String> findBuyerTinByDebitNo(Integer debitNo);

    // ============================================
    // NEW QUERIES FOR DEBIT NOTES
    // ============================================

    // Get all debit notes within date range
    @Query(value = "SELECT * FROM InvoiceUpload WHERE issuedDate >= CONVERT(VARCHAR(10), ?1, 120) AND issuedDate < CONVERT(VARCHAR(10), ?2, 120) ORDER BY issuedDate DESC", nativeQuery = true)
    List<DebitNote> findAllByDateRange(LocalDate startDate, LocalDate endDate);

    // Get debit notes that are successful (UpdateStatus = 1) within date range
    @Query(value = "SELECT * FROM InvoiceUpload WHERE UpdateStatus = 1 AND issuedDate >= CONVERT(VARCHAR(10), ?1, 120) AND issuedDate < CONVERT(VARCHAR(10), ?2, 120) ORDER BY issuedDate DESC", nativeQuery = true)
    List<DebitNote> findSuccessfulDebitNotesByDateRange(LocalDate startDate, LocalDate endDate);

    // Get debit notes that are pending (UpdateStatus != 1 OR UpdateStatus IS NULL) within date range
    @Query(value = "SELECT * FROM InvoiceUpload WHERE (UpdateStatus <> 1 OR UpdateStatus IS NULL) AND issuedDate >= CONVERT(VARCHAR(10), ?1, 120) AND issuedDate < CONVERT(VARCHAR(10), ?2, 120) ORDER BY issuedDate DESC", nativeQuery = true)
    List<DebitNote> findPendingDebitNotesByDateRange(LocalDate startDate, LocalDate endDate);

    // Get debit note by debitNo
    @Query(value = "SELECT * FROM InvoiceUpload WHERE DebitNo = ?1", nativeQuery = true)
    DebitNote findByDebitNote(Integer debitNo);

    // Update buyer email by debitNo
    @Modifying
    @Transactional
    @Query(value = "UPDATE InvoiceUpload SET buyerEmail = ?2 WHERE DebitNo = ?1", nativeQuery = true)
    int updateBuyerEmailByDebitNo(Integer debitNo, String buyerEmail);

    // Update buyer TIN by debitNo
    @Modifying
    @Transactional
    @Query(value = "UPDATE InvoiceUpload SET buyerTin = ?2 WHERE DebitNo = ?1", nativeQuery = true)
    int updateBuyerTinByDebitNo(Integer debitNo, String buyerTin);
}
