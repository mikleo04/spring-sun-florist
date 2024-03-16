package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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

    @Query(
            nativeQuery = true,
            value = """
                    SELECT * FROM m_image
                    WHERE id = :id
                    """
    )
    Optional<Image> getOneById(@Param("id") String id);

    @Modifying
    @Query(
            nativeQuery = true,
            value = """
                    DELETE FROM m_image
                    WHERE id = :id
                    """
    )
    void deleteOneById(@Param("id") String id);


}
