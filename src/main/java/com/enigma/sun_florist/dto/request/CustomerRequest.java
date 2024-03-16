package com.enigma.sun_florist.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class CustomerRequest {

    @NotBlank(message = "name is required")
    private String name;

    @Pattern(message = "number phone is invalid", regexp = "(?:\\+62)?0?8\\d{2}(\\d{8})")
    private String mobilePhoneNo;

    private String address;

//    @Pattern(message = "birth date is invalid", regexp = "^\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$")
    private Date birthDate;

    @Column(name = "status")
    private Boolean status;
}
