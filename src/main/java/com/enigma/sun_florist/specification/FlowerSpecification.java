package com.enigma.sun_florist.specification;

import com.enigma.sun_florist.dto.request.SearchFlowerRequest;
import com.enigma.sun_florist.entity.Flower;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class FlowerSpecification {
    public static Specification<Flower> getSpecification(SearchFlowerRequest request) {
        return(root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%");
                predicates.add(namePredicate);
            }

            if (request.getMinPrice() != null || request.getMaxPrice() != null) {
                if (request.getMinPrice() != null && request.getMaxPrice() != null) {
                    Predicate priceBetween = criteriaBuilder.between(root.get("price"), request.getMinPrice(), request.getMaxPrice());
                    predicates.add(priceBetween);
                }
                else if (request.getMinPrice() != null) {
                    Predicate minPrice = criteriaBuilder.greaterThanOrEqualTo(root.get("price"),request.getMinPrice());
                    predicates.add(minPrice);
                }
                else {
                    Predicate maxPrice = criteriaBuilder.lessThanOrEqualTo(root.get("price"), request.getMaxPrice());
                    predicates.add(maxPrice);
                }
            }

            if (request.getMinStock() != null || request.getMaxStock() != null) {
                if (request.getMinStock() != null && request.getMaxStock() != null) {
                    Predicate stockBetween = criteriaBuilder.between(root.get("stock"), request.getMinStock(), request.getMaxStock());
                    predicates.add(stockBetween);
                }
                else if (request.getMinStock() != null) {
                    Predicate minStock = criteriaBuilder.greaterThanOrEqualTo(root.get("stock"),request.getMinStock());
                    predicates.add(minStock);
                }
                else {
                    Predicate maxStock = criteriaBuilder.lessThanOrEqualTo(root.get("stock"), request.getMaxStock());
                    predicates.add(maxStock);
                }
            }

            return query.where(
                    predicates.toArray(new Predicate[]{})
            ).getRestriction();
        };
    }
}
