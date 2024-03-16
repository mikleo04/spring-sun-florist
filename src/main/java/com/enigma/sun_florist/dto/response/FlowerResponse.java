package com.enigma.sun_florist.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FlowerResponse {

    private String id;

    private String name;

    private Long price;

    private Integer stock;

    private ImageResponse image;

}
