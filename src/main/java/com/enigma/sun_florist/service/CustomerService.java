package com.enigma.sun_florist.service;


import com.enigma.sun_florist.dto.request.CustomerRequest;
import com.enigma.sun_florist.dto.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);
    CustomerResponse getById(String id);
}
