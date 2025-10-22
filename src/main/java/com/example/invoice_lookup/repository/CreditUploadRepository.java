// ============================================
// 1. CreditUploadRepository.java
// ============================================
package com.example.invoice_lookup.repository;

import com.example.invoice_lookup.model.CreditUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditUploadRepository extends JpaRepository<CreditUpload, Integer> {

    // Get all credit notes within date range
    @Query(value = "SELECT * FROM CreditUpload WHERE applicationTime >= CONVERT(VARCHAR(10), ?1, 120) AND applicationTime < CONVERT(VARCHAR(10), ?2, 120) ORDER BY applicationTime DESC", nativeQuery = true)
    List<CreditUpload> findAllByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate);

    // Get credit notes that are successful (UpdateStatus = 1) within date range
    @Query(value = "SELECT * FROM CreditUpload WHERE UpdateStatus = 1 AND applicationTime >= CONVERT(VARCHAR(10), ?1, 120) AND applicationTime < CONVERT(VARCHAR(10), ?2, 120) ORDER BY applicationTime DESC", nativeQuery = true)
    List<CreditUpload> findSuccessfulCreditNotesByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate);

    // Get credit notes that are pending (UpdateStatus != 1) within date range
    @Query(value = "SELECT * FROM CreditUpload WHERE UpdateStatus <> 1 AND applicationTime >= CONVERT(VARCHAR(10), ?1, 120) AND applicationTime < CONVERT(VARCHAR(10), ?2, 120) ORDER BY applicationTime DESC", nativeQuery = true)
    List<CreditUpload> findPendingCreditNotesByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate);
}
