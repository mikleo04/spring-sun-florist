package com.enigma.sun_florist.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchTransactionRequest {

    private Integer page;

    private Integer size;

    private String sortBy;

    private String direction;

    private String date;

    private String startDate;

    private String endDate;

    private String customerId;

}
