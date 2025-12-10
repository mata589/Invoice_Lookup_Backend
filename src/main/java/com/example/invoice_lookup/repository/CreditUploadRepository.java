// ============================================
// Updated CreditUploadRepository with Additional Fields Query
// ============================================
package com.example.invoice_lookup.repository;

import com.example.invoice_lookup.model.CreditUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditUploadRepository extends JpaRepository<CreditUpload, Integer> {

    // ✅ NEW: Get all credit notes with additional fields from external database
    @Query(value = """
        SELECT 
            c.*,
            (SELECT TOP 1 log_user_id FROM [10.9.0.131].FootPrintInsure.dbo.log_policy WHERE log_no = c.DebitNo) AS Doneby,
            (SELECT pol_altpolno FROM [10.9.0.131].FootPrintInsure.dbo.policy WHERE pol_log_no = c.DebitNo) AS Policyno,
            (SELECT pol_debit_no FROM [10.9.0.131].FootPrintInsure.dbo.policy WHERE pol_log_no = c.DebitNo) AS DebitRef
        FROM CreditUpload c
        WHERE c.applicationTime >= CONVERT(VARCHAR(10), ?1, 120) 
        AND c.applicationTime < CONVERT(VARCHAR(10), ?2, 120) 
        ORDER BY c.applicationTime DESC
        """, nativeQuery = true)
    List<Object[]> findAllByDateRangeWithAdditionalFields(java.time.LocalDate startDate, java.time.LocalDate endDate);

    // ✅ NEW: Get successful credit notes with additional fields
    @Query(value = """
        SELECT 
            c.*,
            (SELECT TOP 1 log_user_id FROM [10.9.0.131].FootPrintInsure.dbo.log_policy WHERE log_no = c.DebitNo) AS Doneby,
            (SELECT pol_altpolno FROM [10.9.0.131].FootPrintInsure.dbo.policy WHERE pol_log_no = c.DebitNo) AS Policyno,
            (SELECT pol_debit_no FROM [10.9.0.131].FootPrintInsure.dbo.policy WHERE pol_log_no = c.DebitNo) AS DebitRef
        FROM CreditUpload c
        WHERE c.UpdateStatus = 1 
        AND c.applicationTime >= CONVERT(VARCHAR(10), ?1, 120) 
        AND c.applicationTime < CONVERT(VARCHAR(10), ?2, 120) 
        ORDER BY c.applicationTime DESC
        """, nativeQuery = true)
    List<Object[]> findSuccessfulCreditNotesByDateRangeWithAdditionalFields(java.time.LocalDate startDate, java.time.LocalDate endDate);

    // ✅ NEW: Get pending credit notes with additional fields
    @Query(value = """
        SELECT 
            c.*,
            (SELECT TOP 1 log_user_id FROM [10.9.0.131].FootPrintInsure.dbo.log_policy WHERE log_no = c.DebitNo) AS Doneby,
            (SELECT pol_altpolno FROM [10.9.0.131].FootPrintInsure.dbo.policy WHERE pol_log_no = c.DebitNo) AS Policyno,
            (SELECT pol_debit_no FROM [10.9.0.131].FootPrintInsure.dbo.policy WHERE pol_log_no = c.DebitNo) AS DebitRef
        FROM CreditUpload c
        WHERE c.UpdateStatus <> 1 
        AND c.applicationTime >= CONVERT(VARCHAR(10), ?1, 120) 
        AND c.applicationTime < CONVERT(VARCHAR(10), ?2, 120) 
        ORDER BY c.applicationTime DESC
        """, nativeQuery = true)
    List<Object[]> findPendingCreditNotesByDateRangeWithAdditionalFields(java.time.LocalDate startDate, java.time.LocalDate endDate);

    // ✅ ORIGINAL METHODS (kept for backward compatibility)
    @Query(value = "SELECT * FROM CreditUpload WHERE applicationTime >= CONVERT(VARCHAR(10), ?1, 120) AND applicationTime < CONVERT(VARCHAR(10), ?2, 120) ORDER BY applicationTime DESC", nativeQuery = true)
    List<CreditUpload> findAllByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate);

    @Query(value = "SELECT * FROM CreditUpload WHERE UpdateStatus = 1 AND applicationTime >= CONVERT(VARCHAR(10), ?1, 120) AND applicationTime < CONVERT(VARCHAR(10), ?2, 120) ORDER BY applicationTime DESC", nativeQuery = true)
    List<CreditUpload> findSuccessfulCreditNotesByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate);

    @Query(value = "SELECT * FROM CreditUpload WHERE UpdateStatus <> 1 AND applicationTime >= CONVERT(VARCHAR(10), ?1, 120) AND applicationTime < CONVERT(VARCHAR(10), ?2, 120) ORDER BY applicationTime DESC", nativeQuery = true)
    List<CreditUpload> findPendingCreditNotesByDateRange(java.time.LocalDate startDate, java.time.LocalDate endDate);
}