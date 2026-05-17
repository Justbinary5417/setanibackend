package com.justbinary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "withdrawal_records")
public class WithdrawalRecord {

    public enum Status {
        PENDING, APPROVED, DENIED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Double amount;

    private String phoneNumber;

    private String method; // e.g. M-Pesa, Bank, Crypto

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    private LocalDateTime requestedAt = LocalDateTime.now();

    private LocalDateTime resolvedAt;

    // ===== CONSTRUCTORS =====
    public WithdrawalRecord() {}

    public WithdrawalRecord(User user, Double amount, String phoneNumber, String method) {
        this.user        = user;
        this.amount      = amount;
        this.phoneNumber = phoneNumber;
        this.method      = method;
        this.status      = Status.PENDING;
        this.requestedAt = LocalDateTime.now();
    }

    // ===== GETTERS =====
    public Long getId()                  { return id; }
    public User getUser()                { return user; }
    public Double getAmount()            { return amount; }
    public String getPhoneNumber()       { return phoneNumber; }
    public String getMethod()            { return method; }
    public Status getStatus()            { return status; }
    public LocalDateTime getRequestedAt(){ return requestedAt; }
    public LocalDateTime getResolvedAt() { return resolvedAt; }

    // ===== SETTERS =====
    public void setId(Long id)                          { this.id = id; }
    public void setUser(User user)                      { this.user = user; }
    public void setAmount(Double amount)                { this.amount = amount; }
    public void setPhoneNumber(String phoneNumber)      { this.phoneNumber = phoneNumber; }
    public void setMethod(String method)                { this.method = method; }
    public void setStatus(Status status)                { this.status = status; }
    public void setRequestedAt(LocalDateTime requestedAt){ this.requestedAt = requestedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }
}