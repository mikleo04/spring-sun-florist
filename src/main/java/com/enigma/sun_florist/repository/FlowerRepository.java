package com.enigma.sun_florist.repository;

import com.enigma.sun_florist.entity.Flower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, String> {
}
