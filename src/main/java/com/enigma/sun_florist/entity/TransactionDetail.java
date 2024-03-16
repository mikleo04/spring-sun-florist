package com.enigma.sun_florist.entity;

import com.enigma.sun_florist.constant.TableName;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = TableName.TRANSACTION_DETAIL_TABLE)
public class TransactionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    @JsonBackReference
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "flower_id", nullable = false)
    private Flower flower;

    @Column(name = "flower_price", updatable = false, nullable = false)
    private Long flowerPrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

}
