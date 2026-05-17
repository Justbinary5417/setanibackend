package com.justbinary.repository;

import com.justbinary.model.WithdrawalRecord;
import com.justbinary.model.WithdrawalRecord.Status;
import com.justbinary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WithdrawalRepository extends JpaRepository<WithdrawalRecord, Long> {

    List<WithdrawalRecord> findByStatus(Status status);

    List<WithdrawalRecord> findByUser(User user);

    List<WithdrawalRecord> findAllByOrderByRequestedAtDesc();
}