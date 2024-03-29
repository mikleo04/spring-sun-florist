package com.enigma.sun_florist.service;

import com.enigma.sun_florist.entity.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    Image create(MultipartFile multipartFile);
    Resource getById(String id);
    void delete(String id);
}
