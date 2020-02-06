package com.bartoszgajda.mobileplatformdevelopment.util.model;

import java.util.Date;

public class Roadwork {
  private String title;
  private String description;
  private String link;
  private String[] coordinates;
  private Date publicationDate;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String[] getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(String[] coordinates) {
    this.coordinates = coordinates;
  }

  public Date getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(Date publicationDate) {
    this.publicationDate = publicationDate;
  }
}
