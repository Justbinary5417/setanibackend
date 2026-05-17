package com.justbinary.controller;

import com.justbinary.dto.response.WithdrawResponse;
import com.justbinary.service.MpesaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;

@RestController
@RequestMapping("/api/mpesa")
public class MpesaController {

    private final MpesaService mpesaService;

    public MpesaController(MpesaService mpesaService) {
        this.mpesaService = mpesaService;
    }

    @PostMapping("/withdraw")
    public ResponseEntity<WithdrawResponse> withdraw(@RequestBody Map<String, Object> request) {
        try {
            String phoneNumber = (String) request.get("phoneNumber");
            BigDecimal amount = new BigDecimal(request.get("amount").toString());

            if (phoneNumber == null || phoneNumber.isEmpty()) {
                return ResponseEntity.badRequest()
                        .body(WithdrawResponse.failure("Phone number is required"));
            }

            if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
                return ResponseEntity.badRequest()
                        .body(WithdrawResponse.failure("Invalid amount"));
            }

            WithdrawResponse response = mpesaService.initiateWithdrawal(phoneNumber, amount);

            if (response.isSuccess()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body(response);
            }

        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(WithdrawResponse.failure("Error: " + e.getMessage()));
        }
    }

    @GetMapping("/verify/{transactionId}")
    public ResponseEntity<Map<String, Object>> verifyTransaction(
            @PathVariable String transactionId) {
        try {
            boolean verified = mpesaService.verifyTransaction(transactionId);
            return ResponseEntity.ok(Map.of(
                    "transactionId", transactionId,
                    "verified", verified,
                    "status", verified ? "COMPLETED" : "PENDING"
            ));
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<String> callback(@RequestBody Map<String, Object> payload) {
        try {
            // Handle Mpesa B2C result callback
            Map<String, Object> result = (Map<String, Object>) payload.get("Result");
            if (result != null) {
                String resultCode = String.valueOf(result.get("ResultCode"));
                String transactionId = String.valueOf(result.get("TransactionID"));
                // Log or process result here
            }
            return ResponseEntity.ok("Callback received");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Callback error: " + e.getMessage());
        }
    }
}