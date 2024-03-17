package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface TransactionDetailRepository extends JpaRepository<TransactionDetail, String> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO t_transaction_detail (id, flower_price, quantity, flower_id, transaction_id)
                    VALUES (:id, :flowerPrice, :quantity, :flowerId, :transactionId)
                    """
    )
    void create(
            @Param("id") String id,
            @Param("flowerPrice") Long flowerPrice,
            @Param("quantity") Integer quantity,
            @Param("flowerId") String flowerId,
            @Param("transactionId") String transactionId
    );

}
