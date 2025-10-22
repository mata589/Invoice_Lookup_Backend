package com.example.invoice_lookup.repository;
import com.example.invoice_lookup.model.InvoiceUpload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;



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
}