package com.uade.keepstar.controller;

import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.Image;
import com.uade.keepstar.entity.dto.AddFileRequest;
import com.uade.keepstar.entity.dto.ImageResponse;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.service.ImageService;
import com.uade.keepstar.repository.ImageRepository;

import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("images")
public class ImagesController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private ImageRepository imageRepository;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<ImageResponse> displayImage(@RequestParam("id") long id)
            throws IOException, Exception {

        Image image = imageService.viewById(id);
        String encodedString = Base64.getEncoder()
                .encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok().body(
                ImageResponse.builder()
                        .file(encodedString)
                        .id(id)
                        .build()
        );
    }

    @GetMapping("/products/{productId}/images")
    public List<ImageResponse> listImageDtos(@PathVariable Long productId) {
        return imageRepository.findByProduct_IdOrderById(productId).stream()
            .map(img -> {
                try {
                    Blob blob = img.getImage();
                    byte[] bytes = blob.getBytes(1, (int) blob.length());
                    String b64 = Base64.getEncoder().encodeToString(bytes);
                    return ImageResponse.builder()
                            .id(img.getId())
                            .file(b64)
                            .build();
                } catch (Exception e) {
                    throw new RuntimeException("Error leyendo imagen id=" + img.getId(), e);
                }
            })
            .toList();
    }

    @PostMapping()
    public String addImagePost(AddFileRequest request,
                               @RequestParam("product_id") long productId)
            throws IOException, SerialException, Exception, ProductNotFoundException {

        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Image.builder().image(blob).build(), productId);
        return "created";
    }
}
