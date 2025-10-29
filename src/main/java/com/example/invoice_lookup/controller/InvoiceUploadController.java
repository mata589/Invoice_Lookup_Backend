package com.example.invoice_lookup.controller;

import com.example.invoice_lookup.model.*;
import com.example.invoice_lookup.service.CreditNoteService;
import com.example.invoice_lookup.service.DebitNoteService;
import com.example.invoice_lookup.service.InvoiceUploadService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin // dev
public class InvoiceUploadController {

    private final InvoiceUploadService service;
    private final CreditNoteService creditNoteService;
    private final DebitNoteService debitNoteService;

    public InvoiceUploadController(InvoiceUploadService service,
                                   CreditNoteService creditNoteService,
                                   DebitNoteService debitNoteService) {
        this.service = service;
        this.creditNoteService = creditNoteService;
        this.debitNoteService = debitNoteService;
    }

    @PostMapping("/getInvoice")
    public Map<String, Object> getInvoice(@RequestBody InvoiceRequest request) {
        String debitNoStr = request.getDebitNo();
        Map<String, Object> response = new HashMap<>();

        try {
            Integer debitNo = Integer.parseInt(debitNoStr);
            Optional<InvoiceUpload> recordOpt = service.getByDebitNo(debitNo);
            if (recordOpt.isPresent()) {
                InvoiceUpload record = recordOpt.get();
                if (record.getInvoiceNo() != null && !record.getInvoiceNo().isEmpty()) {
                    response.put("status", "success");
                    response.put("invoiceNo", record.getInvoiceNo());
                } else {
                    response.put("status", "error");
                    response.put("message", record.getUpdateComments());
                }
            } else {
                response.put("status", "not_found");
                response.put("message", "No record found for DebitNo");
            }
        } catch (NumberFormatException e) {
            response.put("status", "error");
            response.put("message", "Invalid DebitNo format");
        }

        return response;
    }

    @PostMapping("/resetTransaction")
    public Map<String, Object> resetTransaction(@RequestBody ResetTransactionRequest request) {
        String debitNoStr = request.getDebitNo();
        Map<String, Object> response = new HashMap<>();

        try {
            Integer debitNo = Integer.parseInt(debitNoStr);
            service.resetTransaction(debitNo);
            response.put("status", "success");
            response.put("message", "Transaction reset successfully");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to reset transaction");
        }

        return response;
    }

    @PostMapping("/clearFolios")
    public Map<String, Object> clearFolios(@RequestBody ClearFolioRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            service.clearFolios(request.getFolioNumbers());
            response.put("status", "success");
            response.put("message", "Records cleared successfully.");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to clear records: " + e.getMessage());
        }
        return response;
    }

    @PostMapping("/processVatProject")
    public Map<String, Object> processVatProject(@RequestBody VatProjectRequest request) {
        String debitNoStr = request.getDebitNo();
        Map<String, Object> response = new HashMap<>();

        try {
            Integer debitNo = Integer.parseInt(debitNoStr);
            return service.processVatProject(debitNo);
        } catch (NumberFormatException e) {
            response.put("status", "error");
            response.put("message", "Invalid DebitNo format");
            return response;
        }
    }

    // ============================================
    // CREDIT NOTE ENDPOINTS
    // ============================================

    @GetMapping("/creditNotes/all")
    public Map<String, Object> getAllCreditNotes(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            java.time.LocalDate startDate, endDate;

            if (startDateStr == null || endDateStr == null) {
                endDate = java.time.LocalDate.now();
                startDate = endDate.minusDays(7);
            } else {
                startDate = java.time.LocalDate.parse(startDateStr, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
                endDate = java.time.LocalDate.parse(endDateStr, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
            }

            List<CreditUpload> creditNotes = creditNoteService.getAllCreditNotes(startDate, endDate);
            response.put("status", "success");
            response.put("total", creditNotes.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", creditNotes);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch credit notes: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/creditNotes/successful")
    public Map<String, Object> getSuccessfulCreditNotes(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            java.time.LocalDate startDate, endDate;

            if (startDateStr == null || endDateStr == null) {
                endDate = java.time.LocalDate.now();
                startDate = endDate.minusDays(7);
            } else {
                startDate = java.time.LocalDate.parse(startDateStr, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
                endDate = java.time.LocalDate.parse(endDateStr, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
            }

            List<CreditUpload> creditNotes = creditNoteService.getSuccessfulCreditNotes(startDate, endDate);
            response.put("status", "success");
            response.put("total", creditNotes.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", creditNotes);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch successful credit notes: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/creditNotes/pending")
    public Map<String, Object> getPendingCreditNotes(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            java.time.LocalDate startDate, endDate;

            if (startDateStr == null || endDateStr == null) {
                endDate = java.time.LocalDate.now();
                startDate = endDate.minusDays(7);
            } else {
                startDate = java.time.LocalDate.parse(startDateStr, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
                endDate = java.time.LocalDate.parse(endDateStr, java.time.format.DateTimeFormatter.ISO_LOCAL_DATE);
            }

            List<CreditUpload> creditNotes = creditNoteService.getPendingCreditNotes(startDate, endDate);
            response.put("status", "success");
            response.put("total", creditNotes.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", creditNotes);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch pending credit notes: " + e.getMessage());
        }
        return response;
    }


// DEBIT NOTE ENDPOINTS
// Add these to your existing Controller class
// ============================================

    @GetMapping("/debitNotes/all")
    public Map<String, Object> getAllDebitNotes(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            LocalDate startDate, endDate;

            if (startDateStr == null || endDateStr == null) {
                endDate = LocalDate.now();
                startDate = endDate.minusDays(7);
            } else {
                startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            }

            List<DebitNote> debitNotes = debitNoteService.getAllDebitNotes(startDate, endDate);
            response.put("status", "success");
            response.put("total", debitNotes.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", debitNotes);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch debit notes: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/debitNotes/successful")
    public Map<String, Object> getSuccessfulDebitNotes(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            LocalDate startDate, endDate;

            if (startDateStr == null || endDateStr == null) {
                endDate = LocalDate.now();
                startDate = endDate.minusDays(7);
            } else {
                startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            }

            List<DebitNote> debitNotes = debitNoteService.getSuccessfulDebitNotes(startDate, endDate);
            response.put("status", "success");
            response.put("total", debitNotes.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", debitNotes);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch successful debit notes: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/debitNotes/pending")
    public Map<String, Object> getPendingDebitNotes(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            LocalDate startDate, endDate;

            if (startDateStr == null || endDateStr == null) {
                endDate = LocalDate.now();
                startDate = endDate.minusDays(7);
            } else {
                startDate = LocalDate.parse(startDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
                endDate = LocalDate.parse(endDateStr, DateTimeFormatter.ISO_LOCAL_DATE);
            }

            List<DebitNote> debitNotes = debitNoteService.getPendingDebitNotes(startDate, endDate);
            response.put("status", "success");
            response.put("total", debitNotes.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", debitNotes);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch pending debit notes: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/debitNotes/{debitNo}")
    public Map<String, Object> getDebitNoteByDebitNo(@PathVariable Integer debitNo) {
        Map<String, Object> response = new HashMap<>();
        try {
            DebitNote debitNote = debitNoteService.getDebitNoteByDebitNote(debitNo);
            if (debitNote != null) {
                response.put("status", "success");
                response.put("data", debitNote);
            } else {
                response.put("status", "not_found");
                response.put("message", "Debit note with debitNo " + debitNo + " not found");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch debit note: " + e.getMessage());
        }
        return response;
    }

    // ============================================
    // DEBIT NOTE UPDATE ENDPOINTS (EMAIL & TIN)
    // ============================================

    /**
     * Update Buyer Email for a Debit Note
     * PATCH /api/debitNotes/{debitNo}/email
     * Body: { "value": "new.email@example.com" }
     */
    @PatchMapping("/debitNotes/{debitNo}/email")
    public Map<String, Object> updateBuyerEmail(
            @PathVariable Integer debitNo,
            @RequestBody @Valid UpdateFieldRequest request) {  // <-- Use DTO + @Valid

        Map<String, Object> response = new HashMap<>();
        String newEmail = request.getValue();

        if (newEmail == null || newEmail.trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "Email value is required and cannot be empty");
            return response;
        }

        try {
            boolean updated = debitNoteService.updateBuyerEmailByDebitNo(debitNo, newEmail.trim());
            if (updated) {
                response.put("status", "success");
                response.put("message", "Buyer email updated successfully");
            } else {
                response.put("status", "not_found");
                response.put("message", "Debit note with debitNo " + debitNo + " not found or email unchanged");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to update email: " + e.getMessage());
        }

        return response;
    }

    /**
     * Update Buyer TIN for a Debit Note
     * PATCH /api/debitNotes/{debitNo}/tin
     * Body: { "value": "1001234567" }
     */
    @PatchMapping("/debitNotes/{debitNo}/tin")
    public Map<String, Object> updateBuyerTin(
            @PathVariable Integer debitNo,
            @RequestBody @Valid UpdateFieldRequest request) {

        Map<String, Object> response = new HashMap<>();
        String newTin = request.getValue();

        if (newTin == null || newTin.trim().isEmpty()) {
            response.put("status", "error");
            response.put("message", "TIN value is required and cannot be empty");
            return response;
        }

        try {
            boolean updated = debitNoteService.updateBuyerTinByDebitNo(debitNo, newTin.trim());
            if (updated) {
                response.put("status", "success");
                response.put("message", "Buyer TIN updated successfully");
            } else {
                response.put("status", "not_found");
                response.put("message", "Debit note with debitNo " + debitNo + " not found or TIN unchanged");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to update TIN: " + e.getMessage());
        }

        return response;
    }
}