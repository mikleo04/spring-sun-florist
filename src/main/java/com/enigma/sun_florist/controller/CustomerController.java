package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.CustomerRequest;
import com.enigma.sun_florist.dto.response.CommonResponse;
import com.enigma.sun_florist.dto.response.CustomerResponse;
import com.enigma.sun_florist.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
