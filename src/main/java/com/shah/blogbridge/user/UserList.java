package com.shah.blogbridge.user;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
public class UserList {
    private String name;
    private Set<String> postId;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getPostId() {
        return postId;
    }

    public void setPostId(Set<String> postId) {
        this.postId = postId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
