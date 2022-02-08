package com.codetest.location.controller;

import com.codetest.location.model.Location;
import com.codetest.location.service.LocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class LocationController {

  private final LocationService locationService;

  public LocationController(LocationService locationService) {
    this.locationService = locationService;
  }

  @PostMapping("/location")
  public ResponseEntity<Location> saveLocation(@RequestBody Location location) {
    return ResponseEntity.ok(locationService.save(location));
  }

  @PutMapping("/location")
  public ResponseEntity<Location> updateLocation(@RequestBody Location location) {
    return ResponseEntity.ok(locationService.save(location));
  }

  @DeleteMapping("/location/{id}")
  public ResponseEntity<String> deleteLocation(@PathVariable Long id) {
    try {
      locationService.deleteById(id);
      return ResponseEntity.ok("Deleted sucessfully");
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().body("something went wrong");
    }
  }

  @GetMapping("/location")
  public ResponseEntity<List<Location>> getAllLocations() {
    return ResponseEntity.ok(locationService.findAll());
  }

  @GetMapping("/location/{id}")
  public ResponseEntity<Location> getAllLocations(@PathVariable("id") Long id) {
    return ResponseEntity.ok(locationService.findById(id));
  }
}
