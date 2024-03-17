package com.enigma.sun_florist.service.impl;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.dto.request.SearchTransactionRequest;
import com.enigma.sun_florist.dto.request.TransactionRequest;
import com.enigma.sun_florist.dto.response.TransactionDetailResponse;
import com.enigma.sun_florist.dto.response.TransactionResponse;
import com.enigma.sun_florist.entity.Transaction;
import com.enigma.sun_florist.repository.TransactionRepository;
import com.enigma.sun_florist.service.TransactionDetailService;
import com.enigma.sun_florist.service.TransactionService;
import com.enigma.sun_florist.specification.TransactionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

        Long totalPrice = transactionDetailResponses.stream()
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

    @Override
    @Transactional(readOnly = true)
    public TransactionResponse getById(String id) {
        Transaction transaction = transactionRepository.getOneById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND)
        );

        return convertTransactionToTransactionResponse(transaction);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TransactionResponse> getAll(SearchTransactionRequest request) {
        Sort sorting = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize(), sorting);

        Specification<Transaction> specification = TransactionSpecification.getSpecification(request);
        Page<Transaction> customerPage = transactionRepository.findAll(specification, pageable);

        return customerPage.map(this::convertTransactionToTransactionResponse);
    }

    private TransactionResponse convertTransactionToTransactionResponse(Transaction transaction) {
        List<TransactionDetailResponse> detailResponses = transaction.getTransactionDetails().stream().map(transactionDetail -> {
            return TransactionDetailResponse.builder()
                    .id(transactionDetail.getId())
                    .flowerPrice(transactionDetail.getFlowerPrice())
                    .quantity(transactionDetail.getQuantity())
                    .flowerId(transactionDetail.getFlower().getId())
                    .build();
        }).toList();

        Long totalPrice = detailResponses.stream()
                .mapToLong(value -> (value.getQuantity() * value.getFlowerPrice()))
                .reduce(0, Long::sum);

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerId(transaction.getCustomer().getId())
                .transDate(transaction.getTransDate())
                .detailTransaction(detailResponses)
                .totalPrice(totalPrice)
                .build();
    }
}
