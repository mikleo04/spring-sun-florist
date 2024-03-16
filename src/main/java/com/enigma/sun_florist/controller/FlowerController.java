package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.service.FlowerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = UrlAPI.FLOWER_API)
public class FlowerController {

    private final FlowerService flowerService;

}
