package com.example.invoice_lookup.model;


import java.util.List;

public class ClearFolioRequest {
    private List<Integer> folioNumbers;

    public List<Integer> getFolioNumbers() {
        return folioNumbers;
    }

    public void setFolioNumbers(List<Integer> folioNumbers) {
        this.folioNumbers = folioNumbers;
    }
}
