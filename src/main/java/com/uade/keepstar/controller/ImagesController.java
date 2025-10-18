package com.uade.keepstar.controller;

import org.springframework.web.bind.annotation.RestController;

import com.uade.keepstar.entity.Image;
import com.uade.keepstar.entity.dto.AddFileRequest;
import com.uade.keepstar.entity.dto.ImageResponse;
import com.uade.keepstar.exceptions.ProductNotFoundException;
import com.uade.keepstar.service.ImageService;
import com.uade.keepstar.repository.ImageRepository;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.util.Base64;
import java.util.List;
import java.net.URLConnection;
import java.io.ByteArrayInputStream;
import java.net.URLConnection;
import java.util.Base64;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
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

    @CrossOrigin(origins = "*")
    @GetMapping(value = "{id}")
    public ResponseEntity<byte[]> displayImage(@PathVariable long id) throws Exception {
        Image image = imageService.viewById(id);

        // Obtener los bytes del BLOB
        byte[] bytes = image.getImage().getBytes(1, (int) image.getImage().length());

        // Detectar el tipo MIME (ej: image/webp, image/jpeg, etc.)
        String guessed = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
        String contentType = (guessed != null) ? guessed : "image/webp";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CACHE_CONTROL, "max-age=31536000, immutable") // opcional
                .body(bytes);
    }


    @CrossOrigin(origins = "*")
    @GetMapping(value = "base64/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> displayImageDataUri(@PathVariable long id) throws Exception {
        Image image = imageService.viewById(id);

        byte[] bytes = image.getImage().getBytes(1, (int) image.getImage().length());

        String guessed = URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(bytes));
        String contentType = (guessed != null) ? guessed : "image/webp";

        // 3) Base64 + Data URI
        String base64 = Base64.getEncoder().encodeToString(bytes);
        String dataUri = "data:" + contentType + ";base64," + base64;

        return ResponseEntity.ok()
                .header(HttpHeaders.CACHE_CONTROL, "max-age=31536000, immutable") // opcional: cache
                .body(dataUri);
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
