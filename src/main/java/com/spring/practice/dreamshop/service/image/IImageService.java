package com.spring.practice.dreamshop.service.image;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.dreamshop.dto.ImageDTO;
import com.spring.practice.dreamshop.model.Image;

public interface IImageService {
    Image getById(Long id);

    void deleteById(Long id);

    List<ImageDTO> save(List<MultipartFile> files, Long product_id);

    void update(MultipartFile file, Long image_id);
}
