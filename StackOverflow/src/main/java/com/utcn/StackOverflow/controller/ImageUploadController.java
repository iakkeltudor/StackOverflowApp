package com.utcn.StackOverflow.controller;

import com.utcn.StackOverflow.request.UploadImageRequest;
import com.utcn.StackOverflow.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping
@CrossOrigin("*")
public class ImageUploadController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/images/upload")
    @ResponseBody
    public ResponseEntity<String> uploadImage(@RequestParam MultipartFile file) {
        //MultipartFile file = uploadImageRequest.getFile();
        String fileName = imageService.storeFile(file);
        System.out.println("Image uploaded successfully: " + fileName);
        return ResponseEntity.ok("Image uploaded successfully: " + fileName);
    }

    @GetMapping(value = "/images/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) throws IOException {
        byte[] imageData = imageService.loadFileAsBytes(fileName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(imageData.length);

        return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
    }
}
