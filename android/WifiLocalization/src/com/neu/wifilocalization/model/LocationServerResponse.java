package com.neu.wifilocalization.model;

public class LocationServerResponse extends ServerResponse{

    private Position data;

    public Position getData() {
        return data;
    }

    public void setData(Position data) {
        this.data = data;
    }
}
