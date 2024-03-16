package com.enigma.sun_florist.service.impl;

import com.enigma.sun_florist.dto.request.CustomerRequest;
import com.enigma.sun_florist.dto.response.CustomerResponse;
import com.enigma.sun_florist.repository.CustomerRepository;
import com.enigma.sun_florist.service.CustomerService;
import com.enigma.sun_florist.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

}
