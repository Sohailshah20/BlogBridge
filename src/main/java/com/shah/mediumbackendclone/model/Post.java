package com.shah.mediumbackendclone.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "post")
public class Post {

    private String id;
    private String title;
    private String Content;
    private String image;
    private int voteCount;
    private List<User> voteBy;
    private List<User> savedBy;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public List<User> getVoteBy() {
        return voteBy;
    }

    public void setVoteBy(List<User> voteBy) {
        this.voteBy = voteBy;
    }

    public List<User> getSavedBy() {
        return savedBy;
    }

    public void setSavedBy(List<User> savedBy) {
        this.savedBy = savedBy;
    }
}
