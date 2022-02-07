package com.codetest.location.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer roomNumber;
    private Double square;
    private String description;
    private String address;

    @OneToMany
    private List<LocationImage> images;

    private Double latitude;

    private Double longitude;

    public Location() {
    }

    public Location(Long id, Integer roomNumber, Double square, String description, String address, List<LocationImage> images, Double latitude, Double longitude) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.square = square;
        this.description = description;
        this.address = address;
        this.images = images;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public Double getSquare() {
        return square;
    }

    public void setSquare(Double square) {
        this.square = square;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<LocationImage> getImages() {
        return images;
    }

    public void setImages(List<LocationImage> images) {
        this.images = images;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
