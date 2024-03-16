package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlAPI.TRANSACTION_API)
public class TransactionController {

    private final TransactionService transactionService;

}
