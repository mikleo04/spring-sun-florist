package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    UPDATE m_customer SET
                    name = :name,
                    birth_date = :birthDate,
                    address = :address,
                    mobile_phone_no = :mobilePhone
                    WHERE id = :id
                    """
    )
    void update(
            @Param("id") String id,
            @Param("name") String name,
            @Param("birthDate") Date birthDate,
            @Param("address") String address,
            @Param("mobilePhone") String mobilePhone
    );

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    UPDATE m_customer SET
                    status = false
                    WHERE id = :id
                    """
    )
    void updateStatus(@Param("id") String id);

}
