package com.enigma.sun_florist.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class FlowerRequest {

    @NotBlank(message = "flower name is required")
    private String name;

    @NotNull(message = "price is required")
    @Min(value = 0, message = "price must be greater than or equal 0")
    private Long price;

    @NotNull(message = "stock is required")
    @Min(value = 0, message = "stock must be greater than or equal 0")
    private Integer stock;

    private MultipartFile image;

}
