package com.example.invoice_lookup.model;



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

    // getters and setters here
}
