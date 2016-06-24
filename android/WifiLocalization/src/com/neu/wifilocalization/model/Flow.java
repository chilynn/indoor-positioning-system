package com.neu.wifilocalization.model;

/**
 * 流量
 * @author alin
 *
 */
public class Flow extends BaseModel{

    private int user_id;
    private int position_id;
    private String time;

    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public int getPosition_id() {
        return position_id;
    }
    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

}
