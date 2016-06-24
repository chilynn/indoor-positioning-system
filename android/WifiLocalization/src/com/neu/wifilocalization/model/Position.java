package com.neu.wifilocalization.model;

import java.io.Serializable;

/**
 * 位置
 * @author alin
 *
 */
public class Position extends BaseModel implements Serializable{

    private double latitude;
    private double longitude;
    private int map_x;
    private int map_y;
    private int ahead_degree;
    private String address;
    private String description;
    
    public double getLatitude() {
        return latitude;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public int getMap_x() {
        return map_x;
    }
    public void setMap_x(int map_x) {
        this.map_x = map_x;
    }
    public int getMap_y() {
        return map_y;
    }
    public void setMap_y(int map_y) {
        this.map_y = map_y;
    }
    public int getAhead_degree() {
        return ahead_degree;
    }
    public void setAhead_degree(int ahead_degree) {
        this.ahead_degree = ahead_degree;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
