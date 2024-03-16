package com.enigma.sun_florist.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionDetailRequest {

    @NotBlank(message = "flower id is required")
    private String flowerId;

    @Min(value = 0, message = "quantity must be greater than or equal 0")
    private Integer quantity;

}
