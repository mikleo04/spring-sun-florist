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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Customer", description = "The Customer API. Contains all the operations that can be performed on a customer.")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create a new customer")
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

    @Operation(summary = "Retrieve details of a specific customer by ID")
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

    @Operation(summary = "Retrieve a list of all customers with pagination and filter ability")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonPagingResponse<List<CustomerResponse>>> getAllCustomer(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "name", required = false) String name,
            @RequestParam(name = "status", required = false) Boolean status
    ) {
        SearchCustomerRequest request = SearchCustomerRequest.builder()
                .size(size)
                .page(page)
                .sortBy(sortBy)
                .direction(direction)
                .name(name)
                .status(status)
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

    @Operation(summary = "Update an existing customer by ID")
    @PutMapping(
            path = "/{id}",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<CustomerResponse>> updateCustomer(@PathVariable(name = "id") String id, @RequestBody CustomerRequest request) {
        CustomerResponse customerResponse = customerService.update(request, id);

        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_UPDATE_DATA)
                .data(customerResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Delete customer based on ID using the soft delete method")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<CommonResponse<CustomerResponse>> deleteCustomer(@PathVariable(name = "id") String id){
        customerService.delete(id);
        CommonResponse<CustomerResponse> response = CommonResponse.<CustomerResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_DELETE_DATA)
                .build();

        return ResponseEntity.ok(response);
    }
}
