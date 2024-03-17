package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.SearchTransactionRequest;
import com.enigma.sun_florist.dto.request.TransactionRequest;
import com.enigma.sun_florist.dto.response.*;
import com.enigma.sun_florist.service.TransactionService;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@RequestMapping(path = UrlAPI.TRANSACTION_API)
@Tag(name = "Transaction", description = "The Transaction API. Contains all the operations that can be performed on a transaction.")
public class TransactionController {

    private final TransactionService transactionService;

    @Operation(summary = "Create a new transaction with the details")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> createTransaction(@RequestBody TransactionRequest request) {
        TransactionResponse transactionResponse = transactionService.create(request);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(ResponseMessage.SUCCESS_SAVE_DATA)
                .data(transactionResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Retrieve details of a specific transaction by ID")
    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonResponse<TransactionResponse>> getTransactionById(@PathVariable(name = "id") String id) {
        TransactionResponse transactionResponse = transactionService.getById(id);

        CommonResponse<TransactionResponse> response = CommonResponse.<TransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(transactionResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Retrieve a list of all transactions with pagination and filter ability")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonPagingResponse<List<TransactionResponse>>> getAllTransaction(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            @RequestParam(name = "sortBy", defaultValue = "transDate") String sortBy,
            @RequestParam(name = "direction", defaultValue = "ASC") String direction,
            @RequestParam(name = "date", required = false) @JsonFormat(pattern = "yyyy-MM-dd") String date,
            @RequestParam(name = "startDate", required = false) @JsonFormat(pattern = "yyyy-MM-dd") String startDate,
            @RequestParam(name = "endDate", required = false) @JsonFormat(pattern = "yyyy-MM-dd") String endDate,
            @RequestParam(name = "customerId", required = false) String customerId
    ) {
        SearchTransactionRequest request = SearchTransactionRequest.builder()
                .size(size)
                .page(page)
                .sortBy(sortBy)
                .direction(direction)
                .date(date)
                .startDate(startDate)
                .endDate(endDate)
                .customerId(customerId)
                .build();
        Page<TransactionResponse> transactionResponses = transactionService.getAll(request);

        PagingResponse pagingResponse = PagingResponse.builder()
                .totalPage(transactionResponses.getTotalPages())
                .totalElement(transactionResponses.getTotalElements())
                .page(transactionResponses.getPageable().getPageNumber() + 1)
                .size(transactionResponses.getSize())
                .hasNext(transactionResponses.hasNext())
                .hasPrevious(transactionResponses.hasPrevious())
                .build();

        CommonPagingResponse<List<TransactionResponse>> response = CommonPagingResponse.<List<TransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(ResponseMessage.SUCCESS_GET_DATA)
                .data(transactionResponses.getContent())
                .paging(pagingResponse)
                .build();

        return ResponseEntity.ok(response);
    }

}
