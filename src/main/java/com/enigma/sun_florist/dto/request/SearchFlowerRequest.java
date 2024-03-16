package com.enigma.sun_florist.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class SearchFlowerRequest {
    private Integer page;

    private Integer size;

    private String sortBy;

    private String direction;

    private String name;

    private Integer maxStock;

    private Integer minStock;

    private Integer maxPrice;

    private Integer minPrice;
}
