package edu.cuhk.csci3310.flowerycampus;

public class Spot {
    private String filename;
    private String flower_name_loc;
    private Double latitude;
    private Double longitude;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFlower_name_loc() {
        return flower_name_loc;
    }

    public void setFlower_name_loc(String flower_name_loc) {
        this.flower_name_loc = flower_name_loc;
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

    @Override
    public String toString() {
        return "Spot{" +
                "filename='" + filename + '\'' +
                ", flower_name_loc='" + flower_name_loc + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
