// src/main/java/com/example/invoice_lookup/model/UpdateFieldRequest.java
package com.example.invoice_lookup.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to update a single field (email or TIN)")
public class UpdateFieldRequest {

    @Schema(description = "New value for the field", example = "john@example.com")
    @NotBlank(message = "Value is required")
    private String value;

    // Constructors
    public UpdateFieldRequest() {}

    public UpdateFieldRequest(String value) {
        this.value = value;
    }

    // Getter & Setter
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}