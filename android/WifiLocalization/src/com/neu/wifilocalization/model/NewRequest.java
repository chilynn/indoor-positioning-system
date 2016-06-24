package com.neu.wifilocalization.model;

/**
 * 新朋友请求
 * @author alin
 *
 */
public class NewRequest {

    private String id;
    private String user_friend_id;
    private String send;
    private String recv;
    private String create_time;
    private User sender;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUser_friend_id() {
        return user_friend_id;
    }
    public void setUser_friend_id(String user_friend_id) {
        this.user_friend_id = user_friend_id;
    }
    public String getSend() {
        return send;
    }
    public void setSend(String send) {
        this.send = send;
    }
    public String getRecv() {
        return recv;
    }
    public void setRecv(String recv) {
        this.recv = recv;
    }
    public String getCreate_time() {
        return create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public User getSender() {
        return sender;
    }
    public void setSender(User sender) {
        this.sender = sender;
    }
    
}
