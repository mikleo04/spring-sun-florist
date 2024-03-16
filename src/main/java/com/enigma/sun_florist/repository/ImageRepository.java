package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    INSERT INTO m_image (id, content_type, name, path, size)
                    VALUES (:id, :contentType, :name, :path, :size)
                    """
    )
    void create(
            @Param("id") String id,
            @Param("contentType") String contentType,
            @Param("name") String name,
            @Param("path") String path,
            @Param("size") Long size
    );


}
