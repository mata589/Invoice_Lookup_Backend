package com.example.invoice_lookup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Medical_Folios")
public class MedicalFolios {

    @Id
    private Integer actualFolioNo;

    public Integer getActualFolioNo() {
        return actualFolioNo;
    }

    public void setActualFolioNo(Integer actualFolioNo) {
        this.actualFolioNo = actualFolioNo;
    }
}
