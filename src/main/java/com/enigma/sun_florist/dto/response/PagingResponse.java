package com.enigma.sun_florist.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PagingResponse {

    private Integer totalPage;

    private Long totalElement;

    private Integer page;

    private Integer size;

    private Boolean hasNext;

    private Boolean hasPrevious;

}
