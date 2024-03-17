package com.enigma.sun_florist.service.impl;

import com.enigma.sun_florist.dto.request.TransactionDetailRequest;
import com.enigma.sun_florist.dto.response.TransactionDetailResponse;
import com.enigma.sun_florist.entity.Flower;
import com.enigma.sun_florist.repository.TransactionDetailRepository;
import com.enigma.sun_florist.service.FlowerService;
import com.enigma.sun_florist.service.TransactionDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionDetailServiceImpl implements TransactionDetailService {

    private final TransactionDetailRepository transactionDetailRepository;
    private final FlowerService flowerService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TransactionDetailResponse> createBulk(List<TransactionDetailRequest> requests, String transactionId) {
        return requests.stream().map(detailRequest -> {

            Flower flower = flowerService.getOneById(detailRequest.getFlowerId());
            String id = UUID.randomUUID().toString();

            transactionDetailRepository.create(id, flower.getPrice(), detailRequest.getQuantity(), detailRequest.getFlowerId(), transactionId);

            Integer currentStock = flower.getStock() - detailRequest.getQuantity();
            flowerService.updateStock(flower.getId(), currentStock);

            return TransactionDetailResponse.builder()
                    .id(id)
                    .flowerId(detailRequest.getFlowerId())
                    .flowerPrice(flower.getPrice())
                    .quantity(detailRequest.getQuantity())
                    .build();

        }).toList();
    }
}
