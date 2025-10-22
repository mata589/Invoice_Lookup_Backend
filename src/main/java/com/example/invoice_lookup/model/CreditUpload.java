

// ============================================
// 2. CreditUpload Model
// ============================================
package com.example.invoice_lookup.model;

import jakarta.persistence.*;

@Entity
@Table(name = "CreditUpload")
public class CreditUpload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer creditId;

    private Integer debitNo;
    private Integer processStatus;
    private Boolean updateStatus;
    private String updateComments;
    private String referenceNo;
    private String approved;
    private String oriInvoiceId;
    private String oriInvoiceNo;
    private String reasonCode;
    private String reason;
    private String applicationTime;
    private String invoiceApplyCategoryCode;
    private String currency;
    private String contactName;
    private String contactMobileNum;
    private String contactEmail;
    private String source;
    private String remarks;
    private String sellersReferenceNo;
    private Float s_netAmount;
    private Float s_taxAmount;
    private Float s_grossAmount;
    private String s_itemCount;
    private String s_modeCode;
    private String s_qrCode;
    private String pw_paymentMode;
    private Float pw_paymentAmount;
    private String pw_orderNumber;
    private String creditnote_invoice;
    private String creditnote_id;
    private String antifakeCode;
    private String qrcodepath;
    private String period;
    private Integer originalDebitNo;

    // ✅ Getters and Setters
    public Integer getCreditId() { return creditId; }
    public void setCreditId(Integer creditId) { this.creditId = creditId; }

    public Integer getDebitNo() { return debitNo; }
    public void setDebitNo(Integer debitNo) { this.debitNo = debitNo; }

    public Integer getProcessStatus() { return processStatus; }
    public void setProcessStatus(Integer processStatus) { this.processStatus = processStatus; }

    public Boolean getUpdateStatus() { return updateStatus; }
    public void setUpdateStatus(Boolean updateStatus) { this.updateStatus = updateStatus; }

    public String getUpdateComments() { return updateComments; }
    public void setUpdateComments(String updateComments) { this.updateComments = updateComments; }

    public String getReferenceNo() { return referenceNo; }
    public void setReferenceNo(String referenceNo) { this.referenceNo = referenceNo; }

    public String getApproved() { return approved; }
    public void setApproved(String approved) { this.approved = approved; }

    public String getOriInvoiceId() { return oriInvoiceId; }
    public void setOriInvoiceId(String oriInvoiceId) { this.oriInvoiceId = oriInvoiceId; }

    public String getOriInvoiceNo() { return oriInvoiceNo; }
    public void setOriInvoiceNo(String oriInvoiceNo) { this.oriInvoiceNo = oriInvoiceNo; }

    public String getReasonCode() { return reasonCode; }
    public void setReasonCode(String reasonCode) { this.reasonCode = reasonCode; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getApplicationTime() { return applicationTime; }
    public void setApplicationTime(String applicationTime) { this.applicationTime = applicationTime; }

    public String getInvoiceApplyCategoryCode() { return invoiceApplyCategoryCode; }
    public void setInvoiceApplyCategoryCode(String invoiceApplyCategoryCode) { this.invoiceApplyCategoryCode = invoiceApplyCategoryCode; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getContactName() { return contactName; }
    public void setContactName(String contactName) { this.contactName = contactName; }

    public String getContactMobileNum() { return contactMobileNum; }
    public void setContactMobileNum(String contactMobileNum) { this.contactMobileNum = contactMobileNum; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getSource() { return source; }
    public void setSource(String source) { this.source = source; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public String getSellersReferenceNo() { return sellersReferenceNo; }
    public void setSellersReferenceNo(String sellersReferenceNo) { this.sellersReferenceNo = sellersReferenceNo; }

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

    public String getS_qrCode() { return s_qrCode; }
    public void setS_qrCode(String s_qrCode) { this.s_qrCode = s_qrCode; }

    public String getPw_paymentMode() { return pw_paymentMode; }
    public void setPw_paymentMode(String pw_paymentMode) { this.pw_paymentMode = pw_paymentMode; }

    public Float getPw_paymentAmount() { return pw_paymentAmount; }
    public void setPw_paymentAmount(Float pw_paymentAmount) { this.pw_paymentAmount = pw_paymentAmount; }

    public String getPw_orderNumber() { return pw_orderNumber; }
    public void setPw_orderNumber(String pw_orderNumber) { this.pw_orderNumber = pw_orderNumber; }

    public String getCreditnote_invoice() { return creditnote_invoice; }
    public void setCreditnote_invoice(String creditnote_invoice) { this.creditnote_invoice = creditnote_invoice; }

    public String getCreditnote_id() { return creditnote_id; }
    public void setCreditnote_id(String creditnote_id) { this.creditnote_id = creditnote_id; }

    public String getAntifakeCode() { return antifakeCode; }
    public void setAntifakeCode(String antifakeCode) { this.antifakeCode = antifakeCode; }

    public String getQrcodepath() { return qrcodepath; }
    public void setQrcodepath(String qrcodepath) { this.qrcodepath = qrcodepath; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public Integer getOriginalDebitNo() { return originalDebitNo; }
    public void setOriginalDebitNo(Integer originalDebitNo) { this.originalDebitNo = originalDebitNo; }
}
