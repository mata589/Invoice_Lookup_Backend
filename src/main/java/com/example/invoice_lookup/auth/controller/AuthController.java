package com.example.invoice_lookup.auth.controller;



import com.example.invoice_lookup.auth.dto.*;
import com.example.invoice_lookup.auth.model.AuditLog;
import com.example.invoice_lookup.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        String ipAddress = getClientIpAddress(httpRequest);
        String userAgent = httpRequest.getHeader("User-Agent");
        return authService.login(request, ipAddress, userAgent);
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request,
                                 HttpServletRequest httpRequest,
                                 @RequestHeader(value = "X-Admin-Username", required = false) String adminUsername) {
        String createdBy = adminUsername != null ? adminUsername : "system";
        return authService.register(request, createdBy);
    }

    @PostMapping("/logout")
    public Map<String, Object> logout(@RequestBody Map<String, String> request, HttpServletRequest httpRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            String username = request.get("username");
            String sessionId = request.get("sessionId");
            String ipAddress = getClientIpAddress(httpRequest);
            String userAgent = httpRequest.getHeader("User-Agent");

            authService.logout(username, ipAddress, userAgent, sessionId);

            response.put("status", "success");
            response.put("message", "Logout successful");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Logout failed: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/audit/user/{userId}")
    public Map<String, Object> getUserAuditLogs(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AuditLog> logs = authService.getUserAuditLogs(userId);
            response.put("status", "success");
            response.put("total", logs.size());
            response.put("data", logs);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch audit logs: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/audit/date-range")
    public Map<String, Object> getAuditLogsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AuditLog> logs = authService.getAuditLogsByDateRange(startDate, endDate);
            response.put("status", "success");
            response.put("total", logs.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", logs);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch audit logs: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/audit/failed-logins")
    public Map<String, Object> getFailedLoginAttempts() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AuditLog> logs = authService.getFailedLoginAttempts();
            response.put("status", "success");
            response.put("total", logs.size());
            response.put("data", logs);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch failed login attempts: " + e.getMessage());
        }
        return response;
    }

    @GetMapping("/audit/application")
    public Map<String, Object> getApplicationAuditLogs(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<AuditLog> logs = authService.getApplicationAuditLogs(startDate, endDate);
            response.put("status", "success");
            response.put("total", logs.size());
            response.put("startDate", startDate.toString());
            response.put("endDate", endDate.toString());
            response.put("data", logs);
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Failed to fetch application audit logs: " + e.getMessage());
        }
        return response;
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }

        String xRealIp = request.getHeader("X-Real-IP");
        if (xRealIp != null && !xRealIp.isEmpty()) {
            return xRealIp;
        }

        return request.getRemoteAddr();
    }
}