package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO t_transaction (id, trans_date, customer_id)
                    VALUES (:id, :transDate, :customerId)
                    """
    )
    void create(
            @Param("id") String id,
            @Param("transDate") Date transDate,
            @Param("customerId") String customerId
    );
}
