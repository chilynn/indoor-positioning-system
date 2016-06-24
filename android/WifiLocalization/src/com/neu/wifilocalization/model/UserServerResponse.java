package com.neu.wifilocalization.model;

/*
 * 登陆服务器响应
 */
public class UserServerResponse extends ServerResponse{

    private User data;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

}
