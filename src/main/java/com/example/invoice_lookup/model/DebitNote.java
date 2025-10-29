// ============================================
// DebitNote Model
// ============================================
package com.example.invoice_lookup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "InvoiceUpload")
public class DebitNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer debitNo;
    private Integer processStatus;
    private Boolean updateStatus;
    private String updateComments;
    private String invoiceNo;
    private String antifakeCode;
    private String deviceNo;
    private String issuedDate;
    private String operator;
    private String currency;
    private String oriInvoiceId;
    private String invoiceType;
    private String invoiceKind;
    private String dataSource;
    private String invoiceIndustryCode;
    private Integer isBatch;
    private String buyerTin;
    private String buyerNinBrn;
    private String buyerPassportNum;
    private String buyerLegalName;
    private String buyerBusinessName;
    private String buyerAddress;
    private String buyerEmail;
    private String buyerMobilePhone;
    private String buyerLinePhone;
    private String buyerPlaceOfBusi;
    private String buyerType;
    private String buyerCitizenship;
    private String buyerSector;
    private String buyerReferenceNo;
    private Float s_netAmount;
    private Float s_taxAmount;
    private Float s_grossAmount;
    private String s_itemCount;
    private String s_modeCode;
    private String s_remarks;
    private String s_qrCode;
    private String pw_paymentMode;
    private Float pw_paymentAmount;
    private String pw_orderNumber;
    private String reason;
    private String reasonCode;
    private String originalInvoiceId;
    private String qrcodepath;
    private String period;

    // ✅ Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Integer getDebitNo() { return debitNo; }
    public void setDebitNo(Integer debitNo) { this.debitNo = debitNo; }

    public Integer getProcessStatus() { return processStatus; }
    public void setProcessStatus(Integer processStatus) { this.processStatus = processStatus; }

    public Boolean getUpdateStatus() { return updateStatus; }
    public void setUpdateStatus(Boolean updateStatus) { this.updateStatus = updateStatus; }

    public String getUpdateComments() { return updateComments; }
    public void setUpdateComments(String updateComments) { this.updateComments = updateComments; }

    public String getInvoiceNo() { return invoiceNo; }
    public void setInvoiceNo(String invoiceNo) { this.invoiceNo = invoiceNo; }

    public String getAntifakeCode() { return antifakeCode; }
    public void setAntifakeCode(String antifakeCode) { this.antifakeCode = antifakeCode; }

    public String getDeviceNo() { return deviceNo; }
    public void setDeviceNo(String deviceNo) { this.deviceNo = deviceNo; }

    public String getIssuedDate() { return issuedDate; }
    public void setIssuedDate(String issuedDate) { this.issuedDate = issuedDate; }

    public String getOperator() { return operator; }
    public void setOperator(String operator) { this.operator = operator; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getOriInvoiceId() { return oriInvoiceId; }
    public void setOriInvoiceId(String oriInvoiceId) { this.oriInvoiceId = oriInvoiceId; }

    public String getInvoiceType() { return invoiceType; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }

    public String getInvoiceKind() { return invoiceKind; }
    public void setInvoiceKind(String invoiceKind) { this.invoiceKind = invoiceKind; }

    public String getDataSource() { return dataSource; }
    public void setDataSource(String dataSource) { this.dataSource = dataSource; }

    public String getInvoiceIndustryCode() { return invoiceIndustryCode; }
    public void setInvoiceIndustryCode(String invoiceIndustryCode) { this.invoiceIndustryCode = invoiceIndustryCode; }

    public Integer getIsBatch() { return isBatch; }
    public void setIsBatch(Integer isBatch) { this.isBatch = isBatch; }

    public String getBuyerTin() { return buyerTin; }
    public void setBuyerTin(String buyerTin) { this.buyerTin = buyerTin; }

    public String getBuyerNinBrn() { return buyerNinBrn; }
    public void setBuyerNinBrn(String buyerNinBrn) { this.buyerNinBrn = buyerNinBrn; }

    public String getBuyerPassportNum() { return buyerPassportNum; }
    public void setBuyerPassportNum(String buyerPassportNum) { this.buyerPassportNum = buyerPassportNum; }

    public String getBuyerLegalName() { return buyerLegalName; }
    public void setBuyerLegalName(String buyerLegalName) { this.buyerLegalName = buyerLegalName; }

    public String getBuyerBusinessName() { return buyerBusinessName; }
    public void setBuyerBusinessName(String buyerBusinessName) { this.buyerBusinessName = buyerBusinessName; }

    public String getBuyerAddress() { return buyerAddress; }
    public void setBuyerAddress(String buyerAddress) { this.buyerAddress = buyerAddress; }

    public String getBuyerEmail() { return buyerEmail; }
    public void setBuyerEmail(String buyerEmail) { this.buyerEmail = buyerEmail; }

    public String getBuyerMobilePhone() { return buyerMobilePhone; }
    public void setBuyerMobilePhone(String buyerMobilePhone) { this.buyerMobilePhone = buyerMobilePhone; }

    public String getBuyerLinePhone() { return buyerLinePhone; }
    public void setBuyerLinePhone(String buyerLinePhone) { this.buyerLinePhone = buyerLinePhone; }

    public String getBuyerPlaceOfBusi() { return buyerPlaceOfBusi; }
    public void setBuyerPlaceOfBusi(String buyerPlaceOfBusi) { this.buyerPlaceOfBusi = buyerPlaceOfBusi; }

    public String getBuyerType() { return buyerType; }
    public void setBuyerType(String buyerType) { this.buyerType = buyerType; }

    public String getBuyerCitizenship() { return buyerCitizenship; }
    public void setBuyerCitizenship(String buyerCitizenship) { this.buyerCitizenship = buyerCitizenship; }

    public String getBuyerSector() { return buyerSector; }
    public void setBuyerSector(String buyerSector) { this.buyerSector = buyerSector; }

    public String getBuyerReferenceNo() { return buyerReferenceNo; }
    public void setBuyerReferenceNo(String buyerReferenceNo) { this.buyerReferenceNo = buyerReferenceNo; }

    public Float getS_netAmount() { return s_netAmount; }
    public void setS_netAmount(Float s_netAmount) { this.s_netAmount = s_netAmount; }

    public Float getS_taxAmount() { return s_taxAmount; }
    public void setS_taxAmount(Float s_taxAmount) { this.s_taxAmount = s_taxAmount; }

    public Float getS_grossAmount() { return s_grossAmount; }
    public void setS_grossAmount(Float s_grossAmount) { this.s_grossAmount = s_grossAmount; }

    public String getS_itemCount() { return s_itemCount; }
    public void setS_itemCount(String s_itemCount) { this.s_itemCount = s_itemCount; }

    public String getS_modeCode() { return s_modeCode; }
    public void setS_modeCode(String s_modeCode) { this.s_modeCode = s_modeCode; }

    public String getS_remarks() { return s_remarks; }
    public void setS_remarks(String s_remarks) { this.s_remarks = s_remarks; }

    public String getS_qrCode() { return s_qrCode; }
    public void setS_qrCode(String s_qrCode) { this.s_qrCode = s_qrCode; }

    public String getPw_paymentMode() { return pw_paymentMode; }
    public void setPw_paymentMode(String pw_paymentMode) { this.pw_paymentMode = pw_paymentMode; }

    public Float getPw_paymentAmount() { return pw_paymentAmount; }
    public void setPw_paymentAmount(Float pw_paymentAmount) { this.pw_paymentAmount = pw_paymentAmount; }

    public String getPw_orderNumber() { return pw_orderNumber; }
    public void setPw_orderNumber(String pw_orderNumber) { this.pw_orderNumber = pw_orderNumber; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getReasonCode() { return reasonCode; }
    public void setReasonCode(String reasonCode) { this.reasonCode = reasonCode; }

    public String getOriginalInvoiceId() { return originalInvoiceId; }
    public void setOriginalInvoiceId(String originalInvoiceId) { this.originalInvoiceId = originalInvoiceId; }

    public String getQrcodepath() { return qrcodepath; }
    public void setQrcodepath(String qrcodepath) { this.qrcodepath = qrcodepath; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}