package com.enigma.sun_florist.service;

import com.enigma.sun_florist.dto.request.FlowerRequest;
import com.enigma.sun_florist.dto.response.FlowerResponse;

public interface FlowerService {
    FlowerResponse create(FlowerRequest request);
    FlowerResponse getById(String id);
}
