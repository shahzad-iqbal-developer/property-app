package com.codetest.location;

import com.codetest.location.model.Location;
import com.codetest.location.model.LocationImage;
import com.codetest.location.repository.LocationRepository;
import com.codetest.location.service.LocationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class LocationApplicationTests {

	@Autowired
	private LocationService locationService;

	@MockBean
	private LocationRepository locationRepository;

	@Test
	public void findAllTest() {
		List<LocationImage> locationImageList = Arrays.asList(new LocationImage(1l,"demo.png","/image/demo.png"),new LocationImage(2l,"demo2.png","/image/demo2.png"));
		when(locationRepository.findAll()).thenReturn(Stream.of(
				new Location(1l,1234,1234d,"Description Goes Here","Address Goes Here",locationImageList,24.862954377705687,66.99982688023567),
				new Location(2l,1234,1234d,"Description Goes Here","Address Goes Here",locationImageList,24.862954377705687,66.99982688023567),
				new Location(3l,1234,1234d,"Description Goes Here","Address Goes Here",locationImageList,24.862954377705687,66.99982688023567)
		).collect(Collectors.toList()));
		assertEquals(3,locationService.findAll().size());
	}

	@Test
	public void findByIdTest() {
		Long id = 1l;
		List<LocationImage> locationImageList = Arrays.asList(new LocationImage(1l,"demo.png","/image/demo.png"),new LocationImage(2l,"demo2.png","/image/demo2.png"));
		Location location = new Location(1l,1234,1234d,"Description Goes Here","Address Goes Here",locationImageList,24.862954377705687,66.99982688023567);
		when(locationRepository.findById(id)).thenReturn(Optional.of(location));
		assertEquals(location,locationService.findById(id));
	}

	@Test
	public void saveTest(){
		List<LocationImage> locationImageList = Arrays.asList(new LocationImage(1l,"demo.png","/image/demo.png"),new LocationImage(2l,"demo2.png","/image/demo2.png"));
		Location location = new Location(1l,1234,1234d,"Description Goes Here","Address Goes Here",locationImageList,24.862954377705687,66.99982688023567);
		when(locationRepository.save(location)).thenReturn(location);
		assertEquals(location,locationService.save(location));
	}

	@Test
	public void deleteByIdTest(){
		Long id = 1l;
		locationService.deleteById(id);
		verify(locationRepository,times(1)).deleteById(id);
	}

}
