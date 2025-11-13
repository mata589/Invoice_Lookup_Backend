package com.example.invoice_lookup.auth.service;

import com.example.invoice_lookup.auth.dto.*;
import com.example.invoice_lookup.auth.model.AuditLog;
import com.example.invoice_lookup.auth.model.User;
import com.example.invoice_lookup.auth.repository.AuditLogRepository;
import com.example.invoice_lookup.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final AuditLogRepository auditLogRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${app.name:InvoiceLookup}")
    private String applicationName;

    private static final int MAX_FAILED_ATTEMPTS = 5;
    private static final int LOCK_TIME_MINUTES = 30;

    /** ISO-8601 without zone – matches string format in DTOs */
    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    public AuthService(UserRepository userRepository,
                       AuditLogRepository auditLogRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.auditLogRepository = auditLogRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* --------------------------------------------------------------------- */
    /* --------------------------- LOGIN ----------------------------------- */
    /* --------------------------------------------------------------------- */
    @Transactional("authTransactionManager")
    public AuthResponse login(LoginRequest request, String ipAddress, String userAgent) {
        Optional<User> userOpt = userRepository.findByUsername(request.getUsername());

        if (userOpt.isEmpty()) {
            logAudit(null, request.getUsername(), "LOGIN_FAILED", ipAddress, userAgent,
                    "FAILED", "User not found");
            return new AuthResponse("error", "Invalid username or password");
        }

        User user = userOpt.get();

        // Account lock handling
        if (user.getAccountLocked() && user.getLockedUntil() != null) {
            if (LocalDateTime.now().isBefore(user.getLockedUntil())) {
                logAudit(user.getId(), user.getUsername(), "LOGIN_FAILED", ipAddress, userAgent,
                        "FAILED", "Account is locked");
                return new AuthResponse("error", "Account is locked. Please try again later.");
            } else {
                userRepository.lockAccount(user.getId(), false, null);
                userRepository.updateFailedLoginAttempts(user.getId(), 0);
                user.setAccountLocked(false);
                user.setFailedLoginAttempts(0);
            }
        }

        // Inactive user
        if (!user.getActive()) {
            logAudit(user.getId(), user.getUsername(), "LOGIN_FAILED", ipAddress, userAgent,
                    "FAILED", "Account is inactive");
            return new AuthResponse("error", "Account is inactive. Please contact administrator.");
        }

        // Password check
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            int failedAttempts = user.getFailedLoginAttempts() + 1;
            userRepository.updateFailedLoginAttempts(user.getId(), failedAttempts);

            if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
                LocalDateTime lockUntil = LocalDateTime.now().plusMinutes(LOCK_TIME_MINUTES);
                userRepository.lockAccount(user.getId(), true, lockUntil);
                logAudit(user.getId(), user.getUsername(), "ACCOUNT_LOCKED", ipAddress, userAgent,
                        "SUCCESS", "Account locked due to " + MAX_FAILED_ATTEMPTS + " failed attempts");
                return new AuthResponse("error",
                        "Account locked due to multiple failed attempts. Try again in " + LOCK_TIME_MINUTES + " minutes.");
            }

            logAudit(user.getId(), user.getUsername(), "LOGIN_FAILED", ipAddress, userAgent,
                    "FAILED", "Invalid password. Attempt " + failedAttempts + " of " + MAX_FAILED_ATTEMPTS);
            return new AuthResponse("error", "Invalid username or password");
        }

        // Successful login
        userRepository.updateFailedLoginAttempts(user.getId(), 0);
        userRepository.updateLastLogin(user.getId(), LocalDateTime.now());

        String sessionId = UUID.randomUUID().toString();
        logAudit(user.getId(), user.getUsername(), "LOGIN", ipAddress, userAgent,
                "SUCCESS", "User logged in successfully", sessionId);

        AuthResponse response = new AuthResponse("success", "Login successful");
        response.setToken(sessionId);
        response.setUser(mapToUserDTO(user));
        return response;
    }

    /* --------------------------------------------------------------------- */
    /* -------------------------- REGISTER --------------------------------- */
    /* --------------------------------------------------------------------- */
    @Transactional("authTransactionManager")
    public AuthResponse register(RegisterRequest request, String createdBy) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse("error", "Username already exists");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse("error", "Email already exists");
        }
        if (!isValidRole(request.getRole())) {
            return new AuthResponse("error", "Invalid role. Valid roles: ADMIN, USER, MANAGER");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setActive(true);
        user.setCreatedBy(createdBy);
        user.setApplicationName(applicationName);

        User savedUser = userRepository.save(user);

        logAudit(savedUser.getId(), savedUser.getUsername(), "USER_CREATED",
                null, null, "SUCCESS", "User created by " + createdBy);

        AuthResponse response = new AuthResponse("success", "User registered successfully");
        response.setUser(mapToUserDTO(savedUser));
        return response;
    }

    /* --------------------------------------------------------------------- */
    /* --------------------------- LOGOUT ---------------------------------- */
    /* --------------------------------------------------------------------- */
    @Transactional("authTransactionManager")
    public void logout(String username, String ipAddress, String userAgent, String sessionId) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            logAudit(userOpt.get().getId(), username, "LOGOUT", ipAddress, userAgent,
                    "SUCCESS", "User logged out", sessionId);
        }
    }

    /* --------------------------------------------------------------------- */
    /* --------------------------- AUDIT ----------------------------------- */
    /* --------------------------------------------------------------------- */

    public List<AuditLogDTO> getUserAuditLogs(Long userId) {
        return auditLogRepository.findByUserId(userId)
                .stream()
                .map(this::mapToAuditLogDTO)
                .collect(Collectors.toList());
    }

    public List<AuditLogDTO> getAuditLogsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByDateRange(startDate, endDate)
                .stream()
                .map(this::mapToAuditLogDTO)
                .collect(Collectors.toList());
    }

    public List<AuditLogDTO> getFailedLoginAttempts() {
        return auditLogRepository.findByActionAndStatus("LOGIN_FAILED", "FAILED")
                .stream()
                .map(this::mapToAuditLogDTO)
                .collect(Collectors.toList());
    }

    public List<AuditLogDTO> getApplicationAuditLogs(LocalDateTime startDate, LocalDateTime endDate) {
        return auditLogRepository.findByApplicationNameAndDateRange(applicationName, startDate, endDate)
                .stream()
                .map(this::mapToAuditLogDTO)
                .collect(Collectors.toList());
    }

    /* --------------------------------------------------------------------- */
    /* --------------------------- HELPERS --------------------------------- */
    /* --------------------------------------------------------------------- */
    private void logAudit(Long userId, String username, String action, String ipAddress,
                          String userAgent, String status, String details) {
        logAudit(userId, username, action, ipAddress, userAgent, status, details, null);
    }

    private void logAudit(Long userId, String username, String action, String ipAddress,
                          String userAgent, String status, String details, String sessionId) {
        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setUsername(username);
        log.setAction(action);
        log.setIpAddress(ipAddress);
        log.setUserAgent(userAgent);
        log.setStatus(status);
        log.setDetails(details);
        log.setSessionId(sessionId);
        log.setApplicationName(applicationName);
        auditLogRepository.save(log);
    }

    /** Map User → UserDTO (String dates) */
    private UserDTO mapToUserDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole());
        dto.setActive(user.getActive());
        dto.setApplicationName(user.getApplicationName());

        dto.setLastLogin(user.getLastLogin() != null
                ? user.getLastLogin().format(DATE_FORMAT)
                : null);
        dto.setCreatedAt(user.getCreatedAt() != null
                ? user.getCreatedAt().format(DATE_FORMAT)
                : null);
        return dto;
    }

    /** Map AuditLog → AuditLogDTO (String timestamp) */
    private AuditLogDTO mapToAuditLogDTO(AuditLog log) {
        AuditLogDTO dto = new AuditLogDTO();
        dto.setId(log.getId());
        dto.setUserId(log.getUserId());
        dto.setUsername(log.getUsername());
        dto.setAction(log.getAction());
        dto.setIpAddress(log.getIpAddress());
        dto.setUserAgent(log.getUserAgent());
        dto.setStatus(log.getStatus());
        dto.setDetails(log.getDetails());
        dto.setSessionId(log.getSessionId());
        dto.setApplicationName(log.getApplicationName());

        dto.setTimestamp(log.getTimestamp() != null
                ? log.getTimestamp().format(DATE_FORMAT)
                : null);
        return dto;
    }

    private boolean isValidRole(String role) {
        return "ADMIN".equals(role) || "USER".equals(role) || "MANAGER".equals(role);
    }
}