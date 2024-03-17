package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.TransactionRequest;
import com.enigma.sun_florist.dto.response.CommonResponse;
import com.enigma.sun_florist.dto.response.TransactionResponse;
import com.enigma.sun_florist.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlAPI.TRANSACTION_API)
public class TransactionController {

    private final TransactionService transactionService;

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

}
