package com.bms.BookMyShow.repository;

import com.bms.BookMyShow.models.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long>
{

    Optional<Payment> findByTransactionId(String transactionId);

}
