package com.enigma.sun_florist.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CommonPagingResponse<T> {

    private Integer statusCode;

    private String message;

    private T data;

    private PagingResponse paging;

}
