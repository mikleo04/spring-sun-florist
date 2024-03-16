package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.CustomerRequest;
import com.enigma.sun_florist.dto.request.SearchCustomerRequest;
import com.enigma.sun_florist.dto.response.CommonPagingResponse;
import com.enigma.sun_florist.dto.response.CommonResponse;
import com.enigma.sun_florist.dto.response.CustomerResponse;
import com.enigma.sun_florist.dto.response.PagingResponse;
import com.enigma.sun_florist.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlAPI.CUSTOMER_API)
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CustomerResponse>> createCustomer(@RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.create(request);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(customerResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CustomerResponse>> getCustomerById(@PathVariable(name = "id") String  id) {
        CustomerResponse customerResponse = customerService.getById(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonPagingResponse<List<CustomerResponse>>> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction
    ) {
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .size(size)
                .page(page)
                .sortBy(sortBy)
                .direction(direction)
                .build();
        Page<CustomerResponse> customerResponses = customerService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(customerResponses.getTotalPages())
                .totalElement(customerResponses.getTotalElements())
                .page(customerResponses.getPageable().getPageNumber() + 1)
                .size(customerResponses.getSize())
                .hasNext(customerResponses.hasNext())
                .hasPrevious(customerResponses.hasPrevious())
                .build();

        CommonPagingResponse<List<CustomerResponse>> response = CommonPagingResponse.<List<CustomerResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(customerResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}
