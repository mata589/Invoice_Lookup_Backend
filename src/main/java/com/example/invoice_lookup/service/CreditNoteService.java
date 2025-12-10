// ============================================
// Updated CreditNoteService with Fixed Type Casting
// ============================================
package com.example.invoice_lookup.service;

import com.example.invoice_lookup.model.CreditUpload;
import com.example.invoice_lookup.repository.CreditUploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreditNoteService {

    private final CreditUploadRepository creditUploadRepository;
    private static final Logger logger = LoggerFactory.getLogger(CreditNoteService.class);

    public CreditNoteService(CreditUploadRepository creditUploadRepository) {
        this.creditUploadRepository = creditUploadRepository;
    }

    // ✅ NEW METHODS with additional fields
    public List<CreditUpload> getAllCreditNotesWithDetails(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        logger.info("Fetching all credit notes with additional details from {} to {}", startDate, endDate);
        List<Object[]> results = creditUploadRepository.findAllByDateRangeWithAdditionalFields(startDate, endDate);
        return mapToCreditUploadList(results);
    }

    public List<CreditUpload> getSuccessfulCreditNotesWithDetails(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        logger.info("Fetching successful credit notes with additional details from {} to {}", startDate, endDate);
        List<Object[]> results = creditUploadRepository.findSuccessfulCreditNotesByDateRangeWithAdditionalFields(startDate, endDate);
        return mapToCreditUploadList(results);
    }

    public List<CreditUpload> getPendingCreditNotesWithDetails(java.time.LocalDate startDate, java.time.LocalDate endDate) {
        logger.info("Fetching pending credit notes with additional details from {} to {}", startDate, endDate);
        List<Object[]> results = creditUploadRepository.findPendingCreditNotesByDateRangeWithAdditionalFields(startDate, endDate);
        return mapToCreditUploadList(results);
    }

    // ✅ Helper method to map Object[] to CreditUpload with additional fields
    private List<CreditUpload> mapToCreditUploadList(List<Object[]> results) {
        return results.stream().map(row -> {
            CreditUpload credit = new CreditUpload();

            // Map all existing fields (indices 0-34 for the 35 columns in CreditUpload table)
            credit.setCreditId(row[0] != null ? (Integer) row[0] : null);
            credit.setDebitNo(row[1] != null ? (Integer) row[1] : null);
            credit.setProcessStatus(row[2] != null ? (Integer) row[2] : null);
            credit.setUpdateStatus(row[3] != null ? (Boolean) row[3] : null);
            credit.setUpdateComments(row[4] != null ? row[4].toString() : null);
            credit.setReferenceNo(row[5] != null ? row[5].toString() : null);
            credit.setApproved(row[6] != null ? row[6].toString() : null);
            credit.setOriInvoiceId(row[7] != null ? row[7].toString() : null);
            credit.setOriInvoiceNo(row[8] != null ? row[8].toString() : null);
            credit.setReasonCode(row[9] != null ? row[9].toString() : null);
            credit.setReason(row[10] != null ? row[10].toString() : null);
            credit.setApplicationTime(row[11] != null ? row[11].toString() : null);
            credit.setInvoiceApplyCategoryCode(row[12] != null ? row[12].toString() : null);
            credit.setCurrency(row[13] != null ? row[13].toString() : null);
            credit.setContactName(row[14] != null ? row[14].toString() : null);
            credit.setContactMobileNum(row[15] != null ? row[15].toString() : null);
            credit.setContactEmail(row[16] != null ? row[16].toString() : null);
            credit.setSource(row[17] != null ? row[17].toString() : null);
            credit.setRemarks(row[18] != null ? row[18].toString() : null);
            credit.setSellersReferenceNo(row[19] != null ? row[19].toString() : null);

            // ✅ FIXED: Cast numeric fields to Double first, then convert to Float
            credit.setS_netAmount(row[20] != null ? ((Number) row[20]).floatValue() : null);
            credit.setS_taxAmount(row[21] != null ? ((Number) row[21]).floatValue() : null);
            credit.setS_grossAmount(row[22] != null ? ((Number) row[22]).floatValue() : null);

            credit.setS_itemCount(row[23] != null ? row[23].toString() : null);
            credit.setS_modeCode(row[24] != null ? row[24].toString() : null);
            credit.setS_qrCode(row[25] != null ? row[25].toString() : null);
            credit.setPw_paymentMode(row[26] != null ? row[26].toString() : null);

            // ✅ FIXED: Cast numeric fields to Double first, then convert to Float
            credit.setPw_paymentAmount(row[27] != null ? ((Number) row[27]).floatValue() : null);

            credit.setPw_orderNumber(row[28] != null ? row[28].toString() : null);
            credit.setCreditnote_invoice(row[29] != null ? row[29].toString() : null);
            credit.setCreditnote_id(row[30] != null ? row[30].toString() : null);
            credit.setAntifakeCode(row[31] != null ? row[31].toString() : null);
            credit.setQrcodepath(row[32] != null ? row[32].toString() : null);
            credit.setPeriod(row[33] != null ? row[33].toString() : null);
            credit.setOriginalDebitNo(row[34] != null ? (Integer) row[34] : null);

            // ✅ Map the new additional fields (last 3 columns from the query)
            credit.setDoneby(row[35] != null ? row[35].toString() : null);
            credit.setPolicyno(row[36] != null ? row[36].toString() : null);
            credit.setDebitref(row[37] != null ? row[37].toString() : null);

            return credit;
        }).collect(Collectors.toList());
    }
}