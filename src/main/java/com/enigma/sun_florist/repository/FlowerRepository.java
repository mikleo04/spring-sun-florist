package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, String> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO m_flower (id, name, price, stock, image_id)
                    VALUES (:id, :name, :price, :stock, :imageId)
                    """
    )
    void create(
            @Param("id") String id,
            @Param("name") String name,
            @Param("price") Long price,
            @Param("stock") Integer stock,
            @Param("imageId") String imageId
    );

}
