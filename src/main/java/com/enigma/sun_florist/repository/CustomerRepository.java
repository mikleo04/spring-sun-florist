package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO m_customer (id, name, birth_date, address, mobile_phone_no, status)
                    VALUES (:id, :name, :birthDate, :address, :mobilePhone, true)
                    """
    )
    void create(
            @Param("id") String id,
            @Param("name") String name,
            @Param("birthDate") Date birthDate,
            @Param("address") String address,
            @Param("mobilePhone") String mobilePhone
            );

    @Query(
            nativeQuery = true,
            value = """
                    SELECT * FROM m_customer WHERE id = :id
                    """
    )
    Optional<Customer> getOneById(@Param("id") String id);
}
