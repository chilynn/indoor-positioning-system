package com.neu.wifilocalization.model;

import java.util.List;

public class RouteServerResponse extends ServerResponse {

    private List<Position> data;

    public List<Position> getData() {
        return data;
    }

    public void setData(List<Position> data) {
        this.data = data;
    }

}
