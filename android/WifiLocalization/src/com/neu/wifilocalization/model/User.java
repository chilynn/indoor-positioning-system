package com.neu.wifilocalization.model;

/**
 * 用户
 * 
 * @author alin
 * 
 */
public class User {

    private String id;
    private int position_id;
    private Position position;
    private String username;
    private String password;
    private String avatar;
    private String email;
    private String phone;
    private String age;
    private int gender;
    private String nickname;
    private String screen_name;
    private String signature;

    private String is_friend;
    private String is_blacklist;
    private boolean isLastLogin;
    private boolean isRememberPass;
    private boolean isAutoLogin;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosition_id() {
        return position_id;
    }

    public void setPosition_id(int position_id) {
        this.position_id = position_id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getScreen_name() {
        return screen_name;
    }

    public void setScreen_name(String screen_name) {
        this.screen_name = screen_name;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getIs_friend() {
        return is_friend;
    }

    public void setIs_friend(String is_friend) {
        this.is_friend = is_friend;
    }

    public String getIs_blacklist() {
        return is_blacklist;
    }

    public void setIs_blacklist(String is_blacklist) {
        this.is_blacklist = is_blacklist;
    }

    public boolean isLastLogin() {
        return isLastLogin;
    }

    public void setLastLogin(boolean isLastLogin) {
        this.isLastLogin = isLastLogin;
    }

    public boolean isRememberPass() {
        return isRememberPass;
    }

    public void setRememberPass(boolean isRememberPass) {
        this.isRememberPass = isRememberPass;
    }

    public boolean isAutoLogin() {
        return isAutoLogin;
    }

    public void setAutoLogin(boolean isAutoLogin) {
        this.isAutoLogin = isAutoLogin;
    }

}
