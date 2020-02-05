package com.bartoszgajda.mobileplatformdevelopment.util.model;

public class Incident {
  private String title;
  private String description;
  private String link;
  private String coordinates;
  private String publicationDate;

  public Incident() {
  }

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

  public String getCoordinates() {
    return coordinates;
  }

  public void setCoordinates(String coordinates) {
    this.coordinates = coordinates;
  }

  public String getPublicationDate() {
    return publicationDate;
  }

  public void setPublicationDate(String publicationDate) {
    this.publicationDate = publicationDate;
  }

  @Override
  public String toString() {
    return "Incident{" +
        "title='" + title + '\'' +
        ", description='" + description + '\'' +
        ", link='" + link + '\'' +
        ", coordinates='" + coordinates + '\'' +
        ", publicationDate='" + publicationDate + '\'' +
        '}';
  }
}
