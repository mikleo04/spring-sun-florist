package com.enigma.sun_florist.service;

import com.enigma.sun_florist.dto.request.TransactionDetailRequest;
import com.enigma.sun_florist.dto.response.TransactionDetailResponse;

import java.util.List;

public interface TransactionDetailService {

    List<TransactionDetailResponse> createBulk(List<TransactionDetailRequest> requests, String transactionId);

}
