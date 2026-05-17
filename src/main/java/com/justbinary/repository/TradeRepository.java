package com.justbinary.repository;

import com.justbinary.model.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TradeRepository 
        extends JpaRepository<Trade, Long> {

    List<Trade> findByUserId(Long userId);
    List<Trade> findByStatus(String status);
}