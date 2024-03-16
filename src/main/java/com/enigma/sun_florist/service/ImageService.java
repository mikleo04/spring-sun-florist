package com.enigma.sun_florist.service;

import com.enigma.sun_florist.dto.response.ImageResponse;
import com.enigma.sun_florist.entity.Image;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image create(MultipartFile multipartFile);
}
