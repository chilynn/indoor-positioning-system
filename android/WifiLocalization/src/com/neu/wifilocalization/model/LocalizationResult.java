package com.neu.wifilocalization.model;

public class LocalizationResult extends ServerResponse{

    private float map_x;
    private float map_y;
    private Node node;
    
    public float getMap_x() {
        return map_x;
    }
    public void setMap_x(float map_x) {
        this.map_x = map_x;
    }
    public float getMap_y() {
        return map_y;
    }
    public void setMap_y(float map_y) {
        this.map_y = map_y;
    }
    public Node getNode() {
        return node;
    }
    public void setNode(Node node) {
        this.node = node;
    }
    
}
