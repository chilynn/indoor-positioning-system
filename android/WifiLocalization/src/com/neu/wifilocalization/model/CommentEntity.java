package com.neu.wifilocalization.model;

public class CommentEntity {

	private String id;
	private User user;
	private String content;
	private String rating;
	private String create_time;
	private String content_type_id;	
	private String content_id;
	
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getCreate_time() {
        return create_time;
    }
    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
    public String getContent_type_id() {
        return content_type_id;
    }
    public void setContent_type_id(String content_type_id) {
        this.content_type_id = content_type_id;
    }
    public String getContent_id() {
        return content_id;
    }
    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }
}
