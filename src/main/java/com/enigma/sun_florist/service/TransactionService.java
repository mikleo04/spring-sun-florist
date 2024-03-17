package com.enigma.sun_florist.service;

import com.enigma.sun_florist.dto.request.TransactionRequest;
import com.enigma.sun_florist.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse create(TransactionRequest request);
}
