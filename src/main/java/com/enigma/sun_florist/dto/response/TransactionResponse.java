package com.enigma.sun_florist.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TransactionResponse {

    private String id;

    private String customerId;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date transDate;

    private List<TransactionDetailResponse> detailTransaction;

}
