package com.enigma.sun_florist.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionRequest {

    @NotBlank(message = "customer id is required")
    private String customerId;

    @NotNull(message = "detail is required")
    private List<TransactionDetailRequest> transactionDetails;

}
