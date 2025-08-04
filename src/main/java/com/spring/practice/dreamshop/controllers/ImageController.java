package com.spring.practice.dreamshop.controllers;

import java.sql.SQLException;
import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spring.practice.dreamshop.dto.ImageDTO;
import com.spring.practice.dreamshop.exception.NotFoundException;
import com.spring.practice.dreamshop.model.Image;
import com.spring.practice.dreamshop.response.APIResponse;
import com.spring.practice.dreamshop.service.image.IImageService;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageInterface;

    @PostMapping("/upload-many")
    public ResponseEntity<APIResponse> save(@RequestParam List<MultipartFile> files,
            @RequestParam Long product_id) {

        try {
            List<ImageDTO> imageDtos = imageInterface.save(files, product_id);

            return ResponseEntity.ok(new APIResponse(true, "images created successfully", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new APIResponse(false, e.getMessage(), null));
        }

    }

    @GetMapping("/download/{image_id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable Long image_id) throws SQLException {
        Image image = imageInterface.getById(image_id);

        ByteArrayResource byteArray = new ByteArrayResource(
                image.getBlog().getBytes(1, (int) image.getBlog().length()));

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getType()))
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; name=\"" + image.getName() + "\"")
                .body(byteArray);
    }

    @PutMapping("/update/{image_id}")
    public ResponseEntity<APIResponse> update(@PathVariable Long image_id, @RequestBody MultipartFile file) {
        try {
            Image image = imageInterface.getById(image_id);

            if (image != null) {
                imageInterface.update(file, image_id);
                return ResponseEntity.ok(new APIResponse(true, "image updated successfully", image));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse(false, "Internal server error", null));
    }

    @DeleteMapping("/delete/{image_id}")
    public ResponseEntity<APIResponse> delete(@PathVariable Long image_id) {
        try {
            Image image = imageInterface.getById(image_id);

            if (image != null) {
                imageInterface.deleteById(image_id);
                return ResponseEntity.ok(new APIResponse(true, "image deleted successfully", image));
            }
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(false, e.getMessage(), null));
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new APIResponse(false, "Internal server error", null));
    }

}
