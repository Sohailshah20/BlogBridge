package com.shah.mediumbackendclone.user;

import com.shah.mediumbackendclone.model.Post;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class UserList {
    private String name;
    private List<Post> posts;
    private String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
