package com.enigma.sun_florist.service.impl;

import com.enigma.sun_florist.constant.ResponseMessage;
import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.dto.request.FlowerRequest;
import com.enigma.sun_florist.dto.request.SearchFlowerRequest;
import com.enigma.sun_florist.dto.response.FlowerResponse;
import com.enigma.sun_florist.dto.response.ImageResponse;
import com.enigma.sun_florist.entity.Flower;
import com.enigma.sun_florist.entity.Image;
import com.enigma.sun_florist.repository.FlowerRepository;
import com.enigma.sun_florist.service.FlowerService;
import com.enigma.sun_florist.service.ImageService;
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

import java.util.Optional;
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

    @Override
    public FlowerResponse getById(String id) {
        Optional<Flower> flower = flowerRepository.getOneById(id);
        if (flower.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
        return convertFlowerToFlowerResponse(flower.get());
    }

    @Override
    public Flower getOneById(String id) {
        Optional<Flower> flower = flowerRepository.getOneById(id);
        if (flower.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, ResponseMessage.ERROR_NOT_FOUND);
        return flower.get();
    }

    @Override
    public Page<FlowerResponse> getAll(SearchFlowerRequest request) {
        Sort sorting = Sort.by(Sort.Direction.fromString(request.getDirection()), request.getSortBy());
        Pageable pageable = PageRequest.of(request.getPage()-1, request.getSize(), sorting);
        Page<Flower> flowerPage = flowerRepository.findAll(pageable);
        return flowerPage.map(this::convertFlowerToFlowerResponse);
    }

    private FlowerResponse convertFlowerToFlowerResponse(Flower flower) {
        return FlowerResponse.builder()
                .id(flower.getId())
                .name(flower.getName())
                .price(flower.getPrice())
                .stock(flower.getStock())
                .image(ImageResponse.builder()
                        .url(UrlAPI.FLOWER_IMAGE_API + flower.getImage().getId())
                        .name(flower.getImage().getName())
                        .build())
                .build();
    }
}
