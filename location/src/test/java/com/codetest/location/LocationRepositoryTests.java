package com.codetest.location;

import com.codetest.location.model.Location;
import com.codetest.location.model.LocationImage;
import com.codetest.location.repository.LocationRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class LocationRepositoryTests {

  @Autowired
  private LocationRepository locationDao;

  @Autowired
  private TestEntityManager entityManager;

  @Test
  public void testSaveLocation() {
    Location location = getLocation();
    Location savedInDb = entityManager.persist(location);
    Location getFromDb = locationDao.findById(savedInDb.getId()).get();

    assertThat(getFromDb).isEqualTo(savedInDb);
  }

  @Test
  public void testGetAllLocations() {
    Location location = getLocation();
    Location location2 = getLocation();
    Location location3 = getLocation();
    Location savedInDb = entityManager.persist(location);
    Location savedInDb2 = entityManager.persist(location2);
    Location savedInDb3 = entityManager.persist(location3);
    List<Location> getFromDb = locationDao.findAll();

    assertThat(getFromDb.size()).isEqualTo(3);
  }

  @Test
  public void testGetLocationById() {
    Location location = getLocation();
    Location savedInDb = entityManager.persist(location);
    Location getFromDb = locationDao.findById(savedInDb.getId()).get();

    assertThat(getFromDb).isEqualTo(savedInDb);
  }

  @Test
  public void testDeleteLocationById() {
    Location location = getLocation();
    Location savedInDb = entityManager.persist(location);
    Location getFromDb = locationDao.findById(savedInDb.getId()).get();

    assertThat(getFromDb).isEqualTo(savedInDb);

    locationDao.deleteById(savedInDb.getId());
    Assert.assertNull(locationDao.findById(savedInDb.getId()).orElse(null));
  }

  private Location getLocation() {
    List<LocationImage> locationImageList = Arrays.asList(new LocationImage(1l, "demo.png", "/image/demo.png"), new LocationImage(2l, "demo2.png", "/image/demo2.png"));
    Location location = new Location(1234, 1234d, "Description Goes Here", "Address Goes Here", locationImageList, 24.862954377705687, 66.99982688023567);
    return location;
  }
}
