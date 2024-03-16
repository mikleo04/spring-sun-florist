package com.enigma.sun_florist.service;

import com.enigma.sun_florist.dto.request.FlowerRequest;
import com.enigma.sun_florist.dto.request.SearchFlowerRequest;
import com.enigma.sun_florist.dto.response.FlowerResponse;
import com.enigma.sun_florist.entity.Flower;
import org.springframework.data.domain.Page;

public interface FlowerService {
    FlowerResponse create(FlowerRequest request);
    FlowerResponse getById(String id);
    Flower getOneById(String id);
    Page<FlowerResponse> getAll(SearchFlowerRequest request);
}
