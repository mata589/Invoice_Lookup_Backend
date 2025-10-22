package com.example.invoice_lookup.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;



import com.example.invoice_lookup.model.GoodsDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodsDetailsRepository extends JpaRepository<GoodsDetails, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM GoodsDetails g WHERE g.debitNo IN :debitNos")
    int deleteByDebitNos(@Param("debitNos") List<Integer> debitNos);

//Add this method to GoodsDetailsRepository.java
@Modifying
@Transactional
@Query("UPDATE GoodsDetails g SET g.vatProjectId = :vatProjectId, g.vatProjectName = :vatProjectName WHERE g.debitNo = :debitNo")
void updateVatProjectInfo(@Param("debitNo") Integer debitNo, @Param("vatProjectId") String vatProjectId, @Param("vatProjectName") String vatProjectName);

    @Query("SELECT g.goodsCategoryId FROM GoodsDetails g WHERE g.debitNo = :debitNo")
    Optional<String> findGoodsCategoryIdByDebitNo(@Param("debitNo") Integer debitNo);
}
