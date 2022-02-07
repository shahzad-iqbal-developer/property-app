package com.codetest.location.controller;

import com.codetest.location.model.LocationImage;
import com.codetest.location.repository.LocationImageRepository;
import com.codetest.location.repository.LocationRepository;
import com.codetest.location.service.ImageStoragePersistancePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ImageController {

    private final LocationImageRepository locationImageRepository;

    private final ImageStoragePersistancePort locationImage;

    public ImageController(ImageStoragePersistancePort locationImage, LocationImageRepository locationImageRepository) {
        this.locationImage = locationImage;
        this.locationImageRepository = locationImageRepository;
    }

    @PostMapping("/image")
    public ResponseEntity<LocationImage> saveImage(@RequestParam("image") MultipartFile image){
        String url = locationImage.saveImage(image);
        if(url !=null){
             return ResponseEntity
                     .ok(locationImageRepository.save(
                             new LocationImage(image.getOriginalFilename(),url)));
        }
        return ResponseEntity.internalServerError().body(null);
    }

    @RequestMapping(value = "/image/{filename:.+}", method = RequestMethod.GET)
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("filename") String filename)
            throws IOException {
        return locationImage.getImage(filename);
    }


}
