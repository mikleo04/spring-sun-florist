package com.enigma.sun_florist.service.impl;

import com.enigma.sun_florist.dto.request.TransactionRequest;
import com.enigma.sun_florist.dto.response.TransactionDetailResponse;
import com.enigma.sun_florist.dto.response.TransactionResponse;
import com.enigma.sun_florist.repository.TransactionRepository;
import com.enigma.sun_florist.service.TransactionDetailService;
import com.enigma.sun_florist.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailService transactionDetailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse create(TransactionRequest request) {
        String id = UUID.randomUUID().toString();
        transactionRepository.create(id, new Date(), request.getCustomerId());
        List<TransactionDetailResponse> transactionDetailResponses = transactionDetailService.createBulk(request.getTransactionDetails(), id);

        long totalPrice = transactionDetailResponses.stream()
                .mapToLong(value -> (value.getQuantity() * value.getFlowerPrice()))
                .reduce(0, Long::sum);

        return TransactionResponse.builder()
                .id(id)
                .customerId(request.getCustomerId())
                .transDate(new Date())
                .totalPrice(totalPrice)
                .detailTransaction(transactionDetailResponses)
                .build();
    }
}
