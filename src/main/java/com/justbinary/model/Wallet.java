package com.justbinary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Double balance = 0.0;
    private Double totalDeposited = 0.0;
    private Double totalWithdrawn = 0.0;
    private LocalDateTime updatedAt = LocalDateTime.now();

    // ===== CONSTRUCTORS =====
    public Wallet() {}

    // ===== GETTERS =====
    public Long getId() { return id; }
    public User getUser() { return user; }
    public Double getBalance() { return balance; }
    public Double getTotalDeposited() { return totalDeposited; }
    public Double getTotalWithdrawn() { return totalWithdrawn; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setBalance(Double balance) { this.balance = balance; }
    public void setTotalDeposited(Double totalDeposited) { this.totalDeposited = totalDeposited; }
    public void setTotalWithdrawn(Double totalWithdrawn) { this.totalWithdrawn = totalWithdrawn; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}