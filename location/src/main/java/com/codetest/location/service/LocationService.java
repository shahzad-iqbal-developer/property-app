package com.codetest.location.service;

import com.codetest.location.model.Location;

import java.util.List;

public interface LocationService {
  Location save(Location location);

  void deleteById(Long id);

  List<Location> findAll();

  Location findById(Long id);
}
