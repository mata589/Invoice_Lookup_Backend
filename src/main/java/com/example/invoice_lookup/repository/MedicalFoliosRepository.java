package com.example.invoice_lookup.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


import com.example.invoice_lookup.model.MedicalFolios;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicalFoliosRepository extends JpaRepository<MedicalFolios, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM MedicalFolios m WHERE m.actualFolioNo IN :folioNos")
    int deleteByFolioNos(@Param("folioNos") List<Integer> folioNos);


}
