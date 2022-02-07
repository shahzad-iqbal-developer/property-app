package com.codetest.location.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStoragePersistancePort {
    public String saveImage(MultipartFile file);

    ResponseEntity<InputStreamResource> getImage(String filename) throws IOException;
}
