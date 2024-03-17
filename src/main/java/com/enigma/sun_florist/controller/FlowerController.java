package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.FlowerRequest;
import com.enigma.sun_florist.dto.request.SearchFlowerRequest;
import com.enigma.sun_florist.dto.response.*;
import com.enigma.sun_florist.service.FlowerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlAPI.FLOWER_API)
@Tag(name = "Flower", description = "The Flower API. Contains all the operations that can be performed on a flower.")
public class FlowerController {

    private final FlowerService flowerService;
    private final ObjectMapper objectMapper;

    @Operation(summary = "Create a new flower with multipart form data")
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

    @Operation(summary = "Retrieve details of a specific flower by ID")
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<FlowerResponse>> getFlowerById(@PathVariable(name = "id") String id) {
        FlowerResponse flowerResponse = flowerService.getById(id);
        CommonResponse<FlowerResponse> response = CommonResponse.<FlowerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(flowerResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve a list of all flowers with pagination and filter ability")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonPagingResponse<List<FlowerResponse>>> getAllFlower(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "minPrice", required = false) Long minPrice,
            @RequestParam(name = "maxPrice", required = false) Long maxPrice,
            @RequestParam(name = "minStock", required = false) Integer minStock,
            @RequestParam(name = "maxStock", required = false) Integer maxStock
    ) {
        SearchFlowerRequest request = SearchFlowerRequest.builder()
                .size(size)
                .page(page)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .minStock(minStock)
                .maxStock(maxStock)
                .build();
        Page<FlowerResponse> flowerResponses = flowerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(flowerResponses.getTotalPages())
                .totalElement(flowerResponses.getTotalElements())
                .page(flowerResponses.getPageable().getPageNumber() + 1)
                .size(flowerResponses.getSize())
                .hasNext(flowerResponses.hasNext())
                .hasPrevious(flowerResponses.hasPrevious())
                .build();

        CommonPagingResponse<List<FlowerResponse>> response = CommonPagingResponse.<List<FlowerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(flowerResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Update an existing flower by ID")
    @PutMapping(
            path = "/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<FlowerResponse>> updateFlower(
            @PathVariable(name = "id") String id,
            @RequestPart(name = "flower") String jsonFlower,
            @RequestPart(name = "image", required = false) MultipartFile image
    ) {
        CommonResponse.CommonResponseBuilder<FlowerResponse> responseBuilder = CommonResponse.builder();

        try {
            FlowerRequest request = objectMapper.readValue(jsonFlower, new TypeReference<>() {});
            request.setImage(image);
            FlowerResponse flowerResponse = flowerService.update(request, id);
            responseBuilder.statusCode(HttpStatus.OK.value());
            responseBuilder.message(ResponseMessage.SUCCESS_UPDATE_DATA);
            responseBuilder.data(flowerResponse);
            return ResponseEntity.status(HttpStatus.OK).body(responseBuilder.build());
        } catch (Exception e) {
            responseBuilder.message(ResponseMessage.ERROR_INTERNAL_SERVER);
            responseBuilder.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBuilder.build());
        }
    }

    @Operation(summary = "Delete flower based on the ID and image it have")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<String>> deleteFlower(@PathVariable(name = "id") String id) {
        flowerService.delete(id);
        CommonResponse<String> response = CommonResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();

        return ResponseEntity.ok(response);
    }

}
