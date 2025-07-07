package com.example.invoice_lookup.controller;

import com.example.invoice_lookup.model.InvoiceRequest;
import com.example.invoice_lookup.model.InvoiceUpload;
import com.example.invoice_lookup.service.InvoiceUploadService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin // dev
public class InvoiceUploadController {

    private final InvoiceUploadService service;

    public InvoiceUploadController(InvoiceUploadService service) {
        this.service = service;
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
    public Map<String, Object> resetTransaction(@RequestBody Map<String, String> payload) {
        String debitNoStr = payload.get("debitNo");
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
}
