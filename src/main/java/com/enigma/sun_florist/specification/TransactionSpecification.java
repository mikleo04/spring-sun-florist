package com.enigma.sun_florist.specification;

import com.enigma.sun_florist.dto.request.SearchTransactionRequest;
import com.enigma.sun_florist.entity.Transaction;
import com.enigma.sun_florist.util.DateFormatedUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransactionSpecification {
    public static Specification<Transaction> getSpecification(SearchTransactionRequest request) {

        return(root, query, criteriaBuilder) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (request.getDate() != null) {
                Date startDate = DateFormatedUtil.parseDate(request.getDate(), "yyyy-MM-dd");
                Date endDate = new Timestamp(startDate.getTime() + 86400000);

                Predicate transDate = criteriaBuilder.between(root.get("transDate"), startDate, endDate);
                predicates.add(transDate);
            }

            if (request.getStartDate() != null || request.getEndDate() != null) {


                if (request.getStartDate() != null && request.getEndDate() != null) {
                    Date startDate = DateFormatedUtil.parseDate(request.getStartDate(), "yyyy-MM-dd");
                    Date endDate = DateFormatedUtil.parseDate(request.getEndDate(), "yyyy-MM-dd");
                    Predicate period = criteriaBuilder.between(root.get("transDate"), startDate, new Timestamp(endDate.getTime() + 86400000));
                    predicates.add(period);
                }
                else if (request.getStartDate() != null) {
                    Date startDate = DateFormatedUtil.parseDate(request.getStartDate(), "yyyy-MM-dd");
                    Predicate periodGreater = criteriaBuilder.greaterThan(root.get("transDate"),startDate);
                    predicates.add(periodGreater);
                }
                else {
                    Date endDate = DateFormatedUtil.parseDate(request.getEndDate(), "yyyy-MM-dd");
                    Predicate periodLess = criteriaBuilder.lessThan(root.get("transDate"), endDate);
                    predicates.add(periodLess);
                }
            }

            if (request.getCustomerId() != null) {
                Predicate customer = criteriaBuilder.equal(root.get("customer").get("id"), request.getCustomerId());
                predicates.add(customer);
            }

            return query.where(
                    predicates.toArray(new Predicate[]{})
            ).getRestriction();

        };

    }
}
