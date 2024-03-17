package com.enigma.sun_florist.controller;

import com.enigma.sun_florist.constant.UrlAPI;
import com.enigma.sun_florist.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlAPI.FLOWER_IMAGE_API)
@Tag(name = "Image", description = "The Image API. Contains operations for download the flower image")
public class ImageController {

    private final ImageService imageService;

    @Operation(summary = "download or get flower image by ID")
    @GetMapping(path = "{id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable(name = "id") String id) {
        Resource imageResponse = imageService.getById(id);
        String headerValue = String.format("attachment; filename=%s", imageResponse.getFilename());
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(imageResponse);
    }

}
