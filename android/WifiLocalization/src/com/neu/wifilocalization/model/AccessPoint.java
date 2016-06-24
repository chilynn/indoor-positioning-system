package com.neu.wifilocalization.model;

/**
 * WiFi热点
 * @author alin
 *
 */
public class AccessPoint extends BaseModel{

    private int position_id;
    private String bssid;
    private String ssid;
    private int rssi;
    
    public int getPosition_id() {
        return position_id;
    }
    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }
    public String getBssid() {
        return bssid;
    }
    public void setBssid(String bssid) {
        this.bssid = bssid;
    }
    public String getSsid() {
        return ssid;
    }
    public void setSsid(String ssid) {
        this.ssid = ssid;
    }
    public int getRssi() {
        return rssi;
    }
    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

}
