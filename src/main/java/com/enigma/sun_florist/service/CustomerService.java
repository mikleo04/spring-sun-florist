package com.enigma.sun_florist.service;


import com.enigma.sun_florist.dto.request.CustomerRequest;
import com.enigma.sun_florist.dto.request.SearchCustomerRequest;
import com.enigma.sun_florist.dto.response.CustomerResponse;
import com.enigma.sun_florist.entity.Customer;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {
    CustomerResponse create(CustomerRequest request);
    CustomerResponse getById(String id);
    Customer getOneById(String id);
    Page<CustomerResponse> getAll(SearchCustomerRequest request);
    CustomerResponse update(CustomerRequest request, String id);
    void delete(String id);
}
