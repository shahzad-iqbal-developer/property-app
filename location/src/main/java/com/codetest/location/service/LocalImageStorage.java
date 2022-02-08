package com.codetest.location.service;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;

@Service
public class LocalImageStorage implements ImageStoragePersistancePort {

  public String saveImage(MultipartFile file) {
    if (file == null) {
      return null;
    }
    try {
      String path = new File(".").getCanonicalPath();
      String unique = String.valueOf(new Timestamp(System.currentTimeMillis()).getTime());

      String UPLOADED_FOLDER_NEW = path + "\\" + "serverFiles\\";
      Path uploadPath = Paths.get(UPLOADED_FOLDER_NEW);

      if (!Files.exists(uploadPath)) {
        Files.createDirectories(uploadPath);
      }

      try (InputStream inputStream = file.getInputStream()) {
        String uniqueFilePath = unique + file.getOriginalFilename();
        Path filePath = uploadPath.resolve(uniqueFilePath);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        return "/image/" + uniqueFilePath;
      } catch (IOException ioe) {
        throw new IOException("Could not save image file: " + file.getOriginalFilename(), ioe);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public ResponseEntity<InputStreamResource> getImage(String filename) throws IOException {
    String path = new File(".").getCanonicalPath();
    String filepath = path + "\\" + "serverFiles\\" + filename;

    File f = new File(filepath);
    Resource file = new UrlResource(f.toURI());
    return ResponseEntity
      .ok()
      .contentLength(file.contentLength())
      .contentType(
        MediaType.parseMediaType("image/JPG"))
      .body(new InputStreamResource(file.getInputStream()));
  }
}
