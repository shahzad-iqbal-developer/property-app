package com.codetest.location.model;

import javax.persistence.*;

@Entity
@Table(name = "image")
public class LocationImage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String url;

  public LocationImage() {
  }

  public LocationImage(String name, String url) {
    this.name = name;
    this.url = url;
  }

  public LocationImage(Long id, String name, String url) {
    this.id = id;
    this.name = name;
    this.url = url;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }
}

