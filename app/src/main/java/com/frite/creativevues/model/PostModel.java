package com.frite.creativevues.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public static PostModel createPost(DocumentSnapshot document) {
        PostModel post = new PostModel(document.getId());
        post.setText(Objects.requireNonNull(document.get("text")).toString());
        post.setAvatar(Objects.requireNonNull(document.get("avatar")).toString());
        post.setUsername(Objects.requireNonNull(document.get("username")).toString());
        post.setModified(Objects.requireNonNull((Boolean) document.get("modified")));
        post.setUser(Objects.requireNonNull(document.get("user")).toString());

        Timestamp postTimestamp = (Timestamp) Objects.requireNonNull(document.get("timestamp"));

        post.setDate(new TimestampModel(postTimestamp));

        // Convert likes into string, then split it and remove square brackets
        List<String> likes = Arrays.asList(
                Objects.requireNonNull(document.get("likes"))
                        .toString()
                        .replace("[", "")
                        .replace("]", "")
                        .split(", ")
        );
        post.setLikes(likes);
        return post;
    }
}
