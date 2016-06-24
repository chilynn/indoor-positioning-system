package com.neu.wifilocalization.model;

import android.location.Location;

public class MapObjectEntity {
    
    private int id;
    private int x;
    private int y;
    private String caption;
    private Location location;
    private Node node;
    
    public MapObjectEntity(int id, Location location, String caption) {
        this.location = location;
        this.caption = caption;
        this.id = id;
    }

    public MapObjectEntity(int id, int x, int y, String caption) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.caption = caption;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Location getLocation() {
        return location;
    }

    public String getCaption() {
        return caption;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    

}
