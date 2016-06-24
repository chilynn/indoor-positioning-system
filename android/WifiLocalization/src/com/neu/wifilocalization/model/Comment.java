package com.neu.wifilocalization.model;

/**
 * 评论
 * @author alin
 *
 */
public class Comment extends BaseModel{

    private int user_id;
    private String content;
    private String time;
    
    public int getUser_id() {
        return user_id;
    }
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    
}
