package com.justbinary.service;

import com.justbinary.config.MpesaConfig;
import com.justbinary.dto.response.WithdrawResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class MpesaService {

    private final MpesaConfig mpesaConfig;
    private final WebClient webClient;

    public MpesaService(MpesaConfig mpesaConfig, WebClient.Builder webClientBuilder) {
        this.mpesaConfig = mpesaConfig;
        this.webClient = webClientBuilder.build();
    }

    private String getAccessToken() {
        try {
            String credentials = mpesaConfig.getConsumerKey() + ":" + mpesaConfig.getConsumerSecret();
            String encoded = Base64.getEncoder().encodeToString(credentials.getBytes());
            String authUrl = mpesaConfig.getBaseUrl() + "/oauth/v1/generate?grant_type=client_credentials";

            Map response = webClient.get()
                    .uri(authUrl)
                    .header("Authorization", "Basic " + encoded)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return response != null ? (String) response.get("access_token") : null;
        } catch (Exception e) {
            return null;
        }
    }

    public WithdrawResponse initiateWithdrawal(String phoneNumber, BigDecimal amount) {
        try {
            String token = getAccessToken();
            if (token == null) {
                return WithdrawResponse.failure("Failed to get access token");
            }

            String transactionId = UUID.randomUUID().toString();
            String b2cUrl = mpesaConfig.getBaseUrl() + "/mpesa/b2c/v1/paymentrequest";

            Map<String, Object> body = new HashMap<>();
            body.put("InitiatorName", mpesaConfig.getB2cInitiatorName());
            body.put("SecurityCredential", mpesaConfig.getB2cSecurityCredential());
            body.put("CommandID", "BusinessPayment");
            body.put("Amount", amount.intValue());
            body.put("PartyA", mpesaConfig.getB2cShortcode());
            body.put("PartyB", phoneNumber);
            body.put("Remarks", "Withdrawal");
            body.put("QueueTimeOutURL", mpesaConfig.getB2cQueueUrl());
            body.put("ResultURL", mpesaConfig.getB2cResultUrl());
            body.put("Occasion", "Withdrawal");

            Map response = webClient.post()
                    .uri(b2cUrl)
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            if (response != null && "0".equals(String.valueOf(response.get("ResponseCode")))) {
                return WithdrawResponse.success(transactionId, amount, null, null, phoneNumber);
            } else {
                return WithdrawResponse.failure("Mpesa request failed");
            }

        } catch (Exception e) {
            return WithdrawResponse.failure("Error: " + e.getMessage());
        }
    }

    public boolean verifyTransaction(String transactionId) {
        try {
            String token = getAccessToken();
            if (token == null) return false;

            String statusUrl = mpesaConfig.getBaseUrl() + "/mpesa/transactionstatus/v1/query";

            Map<String, Object> body = new HashMap<>();
            body.put("Initiator", mpesaConfig.getB2cInitiatorName());
            body.put("SecurityCredential", mpesaConfig.getB2cSecurityCredential());
            body.put("CommandID", "TransactionStatusQuery");
            body.put("TransactionID", transactionId);
            body.put("PartyA", mpesaConfig.getB2cShortcode());
            body.put("IdentifierType", "4");
            body.put("ResultURL", mpesaConfig.getB2cResultUrl());
            body.put("QueueTimeOutURL", mpesaConfig.getB2cQueueUrl());
            body.put("Remarks", "Verify");
            body.put("Occasion", "Verify");

            Map response = webClient.post()
                    .uri(statusUrl)
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .bodyValue(body)
                    .retrieve()
                    .bodyToMono(Map.class)
                    .block();

            return response != null && "0".equals(String.valueOf(response.get("ResponseCode")));

        } catch (Exception e) {
            return false;
        }
    }
}