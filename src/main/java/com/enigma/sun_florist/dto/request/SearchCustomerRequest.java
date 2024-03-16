package com.enigma.sun_florist.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchCustomerRequest {

    private Integer page;

    private Integer size;

    private String sortBy;

    private String direction;

    private String name;

    private Boolean status;

}
