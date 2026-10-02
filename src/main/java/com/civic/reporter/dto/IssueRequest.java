package com.civic.reporter.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
 
public class IssueRequest {
    @NotNull private Long userId;
    @NotBlank @Size(max = 150) private String title;
    @NotBlank @Size(max = 2000) private String description;
    private Double latitude;
    private Double longitude;
    private String photo;
 
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}