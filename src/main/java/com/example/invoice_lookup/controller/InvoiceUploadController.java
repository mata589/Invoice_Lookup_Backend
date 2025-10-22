package com.example.invoice_lookup.controller;

import com.example.invoice_lookup.model.*;
import com.example.invoice_lookup.service.CreditNoteService;
import com.example.invoice_lookup.service.InvoiceUploadService;
import org.springframework.web.bind.annotation.*;

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

    public InvoiceUploadController(InvoiceUploadService service, CreditNoteService creditNoteService) {
        this.service = service;
        this.creditNoteService = creditNoteService;
    }

    @PostMapping("/getInvoice")
    public Map<String, Object> getInvoice(@RequestBody InvoiceRequest request)
 {
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

    @GetMapping("/creditNotes/all")
    public Map<String, Object> getAllCreditNotes(
            @RequestParam(value = "startDate", required = false) String startDateStr,
            @RequestParam(value = "endDate", required = false) String endDateStr) {
        Map<String, Object> response = new HashMap<>();
        try {
            java.time.LocalDate startDate, endDate;

            // If dates not provided, use past 7 days
            if (startDateStr == null || endDateStr == null) {
                endDate = java.time.LocalDate.now();
                startDate = endDate.minusDays(7);
               // logger.info("No date range provided, defaulting to last 7 days: {} to {}", startDate, endDate);
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

            // If dates not provided, use past 7 days
            if (startDateStr == null || endDateStr == null) {
                endDate = java.time.LocalDate.now();
                startDate = endDate.minusDays(7);
                //logger.info("No date range provided, defaulting to last 7 days: {} to {}", startDate, endDate);
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

            // If dates not provided, use past 7 days
            if (startDateStr == null || endDateStr == null) {
                endDate = java.time.LocalDate.now();
                startDate = endDate.minusDays(7);
                //logger.info("No date range provided, defaulting to last 7 days: {} to {}", startDate, endDate);
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

}
