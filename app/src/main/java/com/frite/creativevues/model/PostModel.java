package com.frite.creativevues.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostModel {
    private final String id;
    private String avatar;
    private ArrayList<String> likes;
    private Boolean modified;
    private String text;
    private TimestampModel date;
    private String user;
    private String username;

    public PostModel(String id) {
        this.id = id;
        this.likes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public ArrayList<String> getLikes() {
        return this.likes;
    }

    public void setLikes(List<String> likes) {
        this.likes.addAll(likes);
        this.likes.removeAll(Arrays.asList("", null));
    }

    public Boolean getModified() {
        return modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TimestampModel getDate() {
        return date;
    }

    public void setDate(TimestampModel date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
