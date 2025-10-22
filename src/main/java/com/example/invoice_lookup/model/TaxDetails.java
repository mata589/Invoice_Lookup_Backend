package com.example.invoice_lookup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "taxDetails")
public class TaxDetails {

    @Id
    private Integer debitNo;

    public Integer getDebitNo() {
        return debitNo;
    }

    public void setDebitNo(Integer debitNo) {
        this.debitNo = debitNo;
    }
}
