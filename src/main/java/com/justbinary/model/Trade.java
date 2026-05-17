package com.justbinary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trades")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String asset;
    private String direction;
    private Double amount;
    private Integer durationSeconds;
    private Double entryPrice;
    private Double exitPrice;
    private String status;
    private Double payout;
    private LocalDateTime createdAt = LocalDateTime.now();

    // ===== CONSTRUCTORS =====
    public Trade() {}

    // ===== GETTERS =====
    public Long getId() { return id; }
    public User getUser() { return user; }
    public String getAsset() { return asset; }
    public String getDirection() { return direction; }
    public Double getAmount() { return amount; }
    public Integer getDurationSeconds() { return durationSeconds; }
    public Double getEntryPrice() { return entryPrice; }
    public Double getExitPrice() { return exitPrice; }
    public String getStatus() { return status; }
    public Double getPayout() { return payout; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ===== SETTERS =====
    public void setId(Long id) { this.id = id; }
    public void setUser(User user) { this.user = user; }
    public void setAsset(String asset) { this.asset = asset; }
    public void setDirection(String direction) { this.direction = direction; }
    public void setAmount(Double amount) { this.amount = amount; }
    public void setDurationSeconds(Integer durationSeconds) { this.durationSeconds = durationSeconds; }
    public void setEntryPrice(Double entryPrice) { this.entryPrice = entryPrice; }
    public void setExitPrice(Double exitPrice) { this.exitPrice = exitPrice; }
    public void setStatus(String status) { this.status = status; }
    public void setPayout(Double payout) { this.payout = payout; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}