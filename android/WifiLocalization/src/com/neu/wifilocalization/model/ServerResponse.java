package com.neu.wifilocalization.model;


/**
 * 服务器响应
 * 
 * @author alin
 * 
 */
public class ServerResponse {

    private String state; // 状态
    private String msg; // 消息
    
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
