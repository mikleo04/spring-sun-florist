package com.enigma.sun_florist.service;

import com.enigma.sun_florist.dto.request.SearchTransactionRequest;
import com.enigma.sun_florist.dto.request.TransactionRequest;
import com.enigma.sun_florist.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
    TransactionResponse getById(String id);
}
