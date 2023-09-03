package com.picpaysimplificado2.repositories;

import com.picpaysimplificado2.domain.transactions.Transaction;
import com.picpaysimplificado2.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
