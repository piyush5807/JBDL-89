package com.example.repositories;

import com.example.models.Txn;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TxnRepository extends JpaRepository<Txn, Integer> {

    Txn findByExtTxnId(String extTxnId);
}
