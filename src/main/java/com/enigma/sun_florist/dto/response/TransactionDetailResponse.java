package com.enigma.sun_florist.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionDetailResponse {

    private String id;

    private String flowerId;

    private Long flowerPrice;

    private Integer quantity;

}
