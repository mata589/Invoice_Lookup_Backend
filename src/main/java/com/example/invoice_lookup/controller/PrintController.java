package com.example.invoice_lookup.controller;

import org.springframework.web.bind.annotation.*;
import javax.print.*;
import javax.print.attribute.*;
import javax.print.attribute.standard.*;

@RestController
@RequestMapping("/api")
public class PrintController {

    private static final String PRINTER_NAME = "\\\\IL-20-3Z28\\EPSON TM-T20II Receipt";

    @PostMapping("/print")
    public String print(@RequestBody String content) {
        try {
            // Find the printer by exact name
            PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
            PrintService myPrinter = null;
            for (PrintService printer : printServices) {
                if (printer.getName().equals(PRINTER_NAME)) {
                    myPrinter = printer;
                    break;
                }
            }

            if (myPrinter == null) {
                return "Printer not found!";
            }

            // Print the content
            DocPrintJob job = myPrinter.createPrintJob();
            byte[] bytes = content.getBytes();
            Doc doc = new SimpleDoc(bytes, DocFlavor.BYTE_ARRAY.AUTOSENSE, null);
            job.print(doc, new HashPrintRequestAttributeSet());

            return "Printed successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Printing failed: " + e.getMessage();
        }
    }

    @GetMapping("/printers")
    public String[] listPrinters() {
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        String[] names = new String[printServices.length];
        for (int i = 0; i < printServices.length; i++) {
            names[i] = printServices[i].getName();
        }
        return names;
    }

}
