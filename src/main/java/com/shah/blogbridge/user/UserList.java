package com.shah.blogbridge.user;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class UserList {
    private String name;
    private List<String> postId;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPostId() {
        return postId;
    }

    public void setPostId(List<String> postId) {
        this.postId = postId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
