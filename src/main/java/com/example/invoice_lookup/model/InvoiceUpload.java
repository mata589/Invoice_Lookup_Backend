package com.example.invoice_lookup.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "InvoiceUpload")
public class InvoiceUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "DebitNo")
    private Integer debitNo;

    @Column(name = "ProcessStatus")
    private Integer processStatus;

    @Column(name = "UpdateStatus")
    private Boolean updateStatus;

    @Column(name = "UpdateComments")
    private String updateComments;

    @Column(name = "invoiceNo")
    private String invoiceNo;

    @Column(name = "antifakeCode")
    private String antifakeCode;

    @Column(name = "deviceNo")
    private String deviceNo;

    @Column(name = "issuedDate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime issuedDate;

    @Column(name = "operator")
    private String operator;

    @Column(name = "currency")
    private String currency;

    @Column(name = "oriInvoiceId")
    private String oriInvoiceId;

    @Column(name = "invoiceType")
    private String invoiceType;

    @Column(name = "invoiceKind")
    private String invoiceKind;

    @Column(name = "dataSource")
    private String dataSource;

    @Column(name = "invoiceIndustryCode")
    private String invoiceIndustryCode;

    @Column(name = "isBatch")
    private String isBatch;

    @Column(name = "buyerTin")
    private String buyerTin;

    @Column(name = "buyerNinBrn")
    private String buyerNinBrn;

    @Column(name = "buyerPassportNum")
    private String buyerPassportNum;

    @Column(name = "buyerLegalName")
    private String buyerLegalName;

    @Column(name = "buyerBusinessName")
    private String buyerBusinessName;

    @Column(name = "buyerAddress")
    private String buyerAddress;

    @Column(name = "buyerEmail")
    private String buyerEmail;

    @Column(name = "buyerMobilePhone")
    private String buyerMobilePhone;

    @Column(name = "buyerLinePhone")
    private String buyerLinePhone;

    @Column(name = "buyerPlaceOfBusi")
    private String buyerPlaceOfBusi;

    @Column(name = "buyerType")
    private String buyerType;

    @Column(name = "buyerCitizenship")
    private String buyerCitizenship;

    @Column(name = "buyerSector")
    private String buyerSector;

    @Column(name = "buyerReferenceNo")
    private String buyerReferenceNo;

    @Column(name = "s_netAmount")
    private Double sNetAmount;

    @Column(name = "s_taxAmount")
    private Double sTaxAmount;

    @Column(name = "s_grossAmount")
    private Double sGrossAmount;

    @Column(name = "s_itemCount")
    private Integer sItemCount;

    @Column(name = "s_modeCode")
    private String sModeCode;

    @Column(name = "s_remarks")
    private String sRemarks;

    @Column(name = "s_qrCode")
    private String sQrCode;

    @Column(name = "pw_paymentMode")
    private String pwPaymentMode;

    @Column(name = "pw_paymentAmount")
    private Double pwPaymentAmount;

    @Column(name = "pw_orderNumber")
    private String pwOrderNumber;

    @Column(name = "reason")
    private String reason;

    @Column(name = "reasonCode")
    private String reasonCode;

    @Column(name = "OriginalInvoiceId")
    private String originalInvoiceId;

    @Column(name = "qrcodepath")
    private String qrcodepath;

    @Column(name = "Period")
    private String period;

    // Manual Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDebitNo() {
        return debitNo;
    }

    public void setDebitNo(Integer debitNo) {
        this.debitNo = debitNo;
    }

    public Integer getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(Integer processStatus) {
        this.processStatus = processStatus;
    }

    public Boolean getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Boolean updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getUpdateComments() {
        return updateComments;
    }

    public void setUpdateComments(String updateComments) {
        this.updateComments = updateComments;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getAntifakeCode() {
        return antifakeCode;
    }

    public void setAntifakeCode(String antifakeCode) {
        this.antifakeCode = antifakeCode;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public LocalDateTime getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDateTime issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOriInvoiceId() {
        return oriInvoiceId;
    }

    public void setOriInvoiceId(String oriInvoiceId) {
        this.oriInvoiceId = oriInvoiceId;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceKind() {
        return invoiceKind;
    }

    public void setInvoiceKind(String invoiceKind) {
        this.invoiceKind = invoiceKind;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getInvoiceIndustryCode() {
        return invoiceIndustryCode;
    }

    public void setInvoiceIndustryCode(String invoiceIndustryCode) {
        this.invoiceIndustryCode = invoiceIndustryCode;
    }

    public String getIsBatch() {
        return isBatch;
    }

    public void setIsBatch(String isBatch) {
        this.isBatch = isBatch;
    }

    public String getBuyerTin() {
        return buyerTin;
    }

    public void setBuyerTin(String buyerTin) {
        this.buyerTin = buyerTin;
    }

    public String getBuyerNinBrn() {
        return buyerNinBrn;
    }

    public void setBuyerNinBrn(String buyerNinBrn) {
        this.buyerNinBrn = buyerNinBrn;
    }

    public String getBuyerPassportNum() {
        return buyerPassportNum;
    }

    public void setBuyerPassportNum(String buyerPassportNum) {
        this.buyerPassportNum = buyerPassportNum;
    }

    public String getBuyerLegalName() {
        return buyerLegalName;
    }

    public void setBuyerLegalName(String buyerLegalName) {
        this.buyerLegalName = buyerLegalName;
    }

    public String getBuyerBusinessName() {
        return buyerBusinessName;
    }

    public void setBuyerBusinessName(String buyerBusinessName) {
        this.buyerBusinessName = buyerBusinessName;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public String getBuyerEmail() {
        return buyerEmail;
    }

    public void setBuyerEmail(String buyerEmail) {
        this.buyerEmail = buyerEmail;
    }

    public String getBuyerMobilePhone() {
        return buyerMobilePhone;
    }

    public void setBuyerMobilePhone(String buyerMobilePhone) {
        this.buyerMobilePhone = buyerMobilePhone;
    }

    public String getBuyerLinePhone() {
        return buyerLinePhone;
    }

    public void setBuyerLinePhone(String buyerLinePhone) {
        this.buyerLinePhone = buyerLinePhone;
    }

    public String getBuyerPlaceOfBusi() {
        return buyerPlaceOfBusi;
    }

    public void setBuyerPlaceOfBusi(String buyerPlaceOfBusi) {
        this.buyerPlaceOfBusi = buyerPlaceOfBusi;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public String getBuyerCitizenship() {
        return buyerCitizenship;
    }

    public void setBuyerCitizenship(String buyerCitizenship) {
        this.buyerCitizenship = buyerCitizenship;
    }

    public String getBuyerSector() {
        return buyerSector;
    }

    public void setBuyerSector(String buyerSector) {
        this.buyerSector = buyerSector;
    }

    public String getBuyerReferenceNo() {
        return buyerReferenceNo;
    }

    public void setBuyerReferenceNo(String buyerReferenceNo) {
        this.buyerReferenceNo = buyerReferenceNo;
    }

    public Double getsNetAmount() {
        return sNetAmount;
    }

    public void setsNetAmount(Double sNetAmount) {
        this.sNetAmount = sNetAmount;
    }

    public Double getsTaxAmount() {
        return sTaxAmount;
    }

    public void setsTaxAmount(Double sTaxAmount) {
        this.sTaxAmount = sTaxAmount;
    }

    public Double getsGrossAmount() {
        return sGrossAmount;
    }

    public void setsGrossAmount(Double sGrossAmount) {
        this.sGrossAmount = sGrossAmount;
    }

    public Integer getsItemCount() {
        return sItemCount;
    }

    public void setsItemCount(Integer sItemCount) {
        this.sItemCount = sItemCount;
    }

    public String getsModeCode() {
        return sModeCode;
    }

    public void setsModeCode(String sModeCode) {
        this.sModeCode = sModeCode;
    }

    public String getsRemarks() {
        return sRemarks;
    }

    public void setsRemarks(String sRemarks) {
        this.sRemarks = sRemarks;
    }

    public String getsQrCode() {
        return sQrCode;
    }

    public void setsQrCode(String sQrCode) {
        this.sQrCode = sQrCode;
    }

    public String getPwPaymentMode() {
        return pwPaymentMode;
    }

    public void setPwPaymentMode(String pwPaymentMode) {
        this.pwPaymentMode = pwPaymentMode;
    }

    public Double getPwPaymentAmount() {
        return pwPaymentAmount;
    }

    public void setPwPaymentAmount(Double pwPaymentAmount) {
        this.pwPaymentAmount = pwPaymentAmount;
    }

    public String getPwOrderNumber() {
        return pwOrderNumber;
    }

    public void setPwOrderNumber(String pwOrderNumber) {
        this.pwOrderNumber = pwOrderNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public String getOriginalInvoiceId() {
        return originalInvoiceId;
    }

    public void setOriginalInvoiceId(String originalInvoiceId) {
        this.originalInvoiceId = originalInvoiceId;
    }

    public String getQrcodepath() {
        return qrcodepath;
    }

    public void setQrcodepath(String qrcodepath) {
        this.qrcodepath = qrcodepath;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}