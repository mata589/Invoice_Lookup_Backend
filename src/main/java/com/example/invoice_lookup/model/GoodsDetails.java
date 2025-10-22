package com.example.invoice_lookup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "goodsDetails")
public class GoodsDetails {

    @Id
    private Integer debitNo;

    private String vatProjectId;
    private String vatProjectName;
    private String goodsCategoryId;

    // getters & setters
    public Integer getDebitNo() {
        return debitNo;
    }

    public void setDebitNo(Integer debitNo) {
        this.debitNo = debitNo;
    }

    public String getVatProjectId() {
        return vatProjectId;
    }

    public void setVatProjectId(String vatProjectId) {
        this.vatProjectId = vatProjectId;
    }

    public String getVatProjectName() {
        return vatProjectName;
    }

    public void setVatProjectName(String vatProjectName) {
        this.vatProjectName = vatProjectName;
    }

    public String getGoodsCategoryId() {
        return goodsCategoryId;
    }

    public void setGoodsCategoryId(String goodsCategoryId) {
        this.goodsCategoryId = goodsCategoryId;
    }
}
