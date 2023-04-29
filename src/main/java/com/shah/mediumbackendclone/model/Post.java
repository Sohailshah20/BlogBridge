package com.shah.mediumbackendclone.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "post")
public class Post {

    private String id;
    private String title;
    private String Content;
    private String image;
    private List<User> votesBy;
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
        return votesBy.size();
    }
    public List<User> getVotesBy() {
        return votesBy;
    }

    public void setVotesBy(List<User> votesBy) {
        this.votesBy = votesBy;
    }

    public List<User> getSavedBy() {
        return savedBy;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSavedBy(List<User> savedBy) {
        this.savedBy = savedBy;
    }
}
