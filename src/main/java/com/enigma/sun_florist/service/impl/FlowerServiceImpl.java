package com.enigma.sun_florist.service.impl;

import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.FlowerRequest;
import com.enigma.sun_florist.dto.response.FlowerResponse;
import com.enigma.sun_florist.dto.response.ImageResponse;
import com.enigma.sun_florist.entity.Image;
import com.enigma.sun_florist.repository.FlowerRepository;
import com.enigma.sun_florist.service.FlowerService;
import com.enigma.sun_florist.service.ImageService;
import com.enigma.sun_florist.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FlowerServiceImpl implements FlowerService {

    private final FlowerRepository flowerRepository;
    private final ValidationUtil validationUtil;
    private final ImageService imageService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FlowerResponse create(FlowerRequest request) {
        validationUtil.validate(request);
        Image image = imageService.create(request.getImage());
        String id = UUID.randomUUID().toString();

        flowerRepository.create(id, request.getName(), request.getPrice(), request.getStock(), image.getId());

        ImageResponse imageResponse = ImageResponse.builder()
                .name(image.getName())
                .url(UrlAPI.FLOWER_IMAGE_API + image.getId())
                .build();

        return FlowerResponse.builder()
                .id(id)
                .image(imageResponse)
                .stock(request.getStock())
                .price(request.getPrice())
                .name(request.getName())
                .build();
    }
}
