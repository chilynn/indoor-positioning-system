package com.neu.wifilocalization.model;

import java.io.Serializable;

/**
 * model的基类
 * @author alin
 *
 */
public class BaseModel implements Serializable{

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
