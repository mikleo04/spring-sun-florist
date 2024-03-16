package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.FlowerRequest;
import com.enigma.sun_florist.dto.response.CommonResponse;
import com.enigma.sun_florist.dto.response.FlowerResponse;
import com.enigma.sun_florist.service.FlowerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlAPI.FLOWER_API)
public class FlowerController {

    private final FlowerService flowerService;
    private final ObjectMapper objectMapper;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<FlowerResponse>> createFlower(
            @RequestPart(name = "flower") String jsonFlower,
            @RequestPart(name = "image") MultipartFile image
    ) {
        CommonResponse.CommonResponseBuilder<FlowerResponse> responseBuilder = CommonResponse.builder();

        try {
            FlowerRequest request = objectMapper.readValue(jsonFlower, new TypeReference<>() {
            });
            request.setImage(image);
            FlowerResponse flowerResponse = flowerService.create(request);
            responseBuilder.statusCode(HttpStatus.CREATED.value());
            responseBuilder.message(ResponseMessage.SUCCESS_SAVE_DATA);
            responseBuilder.data(flowerResponse);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseBuilder.build());
        } catch (Exception e) {
            responseBuilder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }

    }

}
