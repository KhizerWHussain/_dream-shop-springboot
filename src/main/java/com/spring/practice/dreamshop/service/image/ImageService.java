package com.spring.practice.dreamshop.service.image;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.dreamshop.dto.ImageDTO;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Image;
import com.spring.practice.dreamshop.model.Product;
import com.spring.practice.dreamshop.repository.ImageRepository;
import com.spring.practice.dreamshop.service.product.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService implements IImageService {
    private final ImageRepository _image;
    private final ProductService productService;

    @Override
    public Image getById(Long id) {
        return _image.findById(id).orElseThrow(() -> new NotFoundException("Image not found"));
    }

    @Override
    public void deleteById(Long id) {
        _image.findById(id).ifPresentOrElse(_image::delete, () -> {
            throw new NotFoundException("Image not found");
        });
    }

    @Override
    public List<ImageDTO> save(List<MultipartFile> files, Long product_id) {
        Product product = productService.read(product_id);

        List<ImageDTO> savedImageDTOs = new ArrayList<ImageDTO>();

        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setName(file.getOriginalFilename());
                image.setType(file.getContentType());
                image.setBlog(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String buildDownloadUrl = "/api/v1/images/download";
                String url = buildDownloadUrl + image.getId();

                image.setDowload_url(url);
                Image savedImage = _image.save(image);

                savedImage.setDowload_url(buildDownloadUrl + savedImage.getId());
                _image.save(savedImage);

                ImageDTO imageDto = new ImageDTO();
                imageDto.setImage_id(savedImage.getId());
                imageDto.setName(savedImage.getName());
                imageDto.setDownload_url(savedImage.getDowload_url());

                savedImageDTOs.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return savedImageDTOs;

    }

    @Override
    public void update(MultipartFile file, Long image_id) {
        Image image = getById(image_id);

        try {
            image.setName(file.getOriginalFilename());
            image.setBlog(new SerialBlob(file.getBytes()));
            _image.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException(e.getMessage());
        }

    }

}
