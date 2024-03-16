package com.enigma.sun_florist.service.impl;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.dto.request.CustomerRequest;
import com.enigma.sun_florist.dto.request.SearchCustomerRequest;
import com.enigma.sun_florist.dto.response.CustomerResponse;
import com.enigma.sun_florist.entity.Customer;
import com.enigma.sun_florist.repository.CustomerRepository;
import com.enigma.sun_florist.service.CustomerService;
import com.enigma.sun_florist.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final ValidationUtil validationUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse create(CustomerRequest request) {
        validationUtil.validate(request);
        String id = UUID.randomUUID().toString();
        customerRepository.create(id, request.getName(), request.getBirthDate(), request.getAddress(), request.getMobilePhoneNo());
        return CustomerResponse.builder()
                .id(id)
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .mobilePhoneNo(request.getMobilePhoneNo())
                .address(request.getAddress())
                .status(true)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getById(String id) {
        Optional<Customer> customer = customerRepository.getOneById(id);
        if (customer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
        return convertCustomerToCustomerResponse(customer.get());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAll(SearchCustomerRequest request) {
        Sort sorting = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize(), sorting);
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return  customerPage.map(this::convertCustomerToCustomerResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CustomerResponse update(CustomerRequest request, String id) {
        getById(id);
        validationUtil.validate(request);
        customerRepository.update(id, request.getName(), request.getBirthDate(), request.getAddress(), request.getMobilePhoneNo());
        return CustomerResponse.builder()
                .id(id)
                .name(request.getName())
                .birthDate(request.getBirthDate())
                .mobilePhoneNo(request.getMobilePhoneNo())
                .address(request.getAddress())
                .status(true)
                .build();
    }


    private CustomerResponse convertCustomerToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .birthDate(customer.getBirthDate())
                .status(customer.getStatus())
                .mobilePhoneNo(customer.getMobilePhoneNo())
                .address(customer.getAddress())
                .build();
    }

}
