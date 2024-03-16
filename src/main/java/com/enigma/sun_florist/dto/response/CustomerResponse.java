package com.enigma.sun_florist.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CustomerResponse {

    private String id;

    private String name;

    private String mobilePhoneNo;

    private String address;

    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    private Boolean status;

}
