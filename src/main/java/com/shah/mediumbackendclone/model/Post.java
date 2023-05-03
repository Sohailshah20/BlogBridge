package com.shah.mediumbackendclone.model;

import com.shah.mediumbackendclone.user.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "post")
public class Post {

    @Id
    private String postId;
    private String ownerId;
    private String title;
    private String Content;
    private String Summary;
    private String image;
    private String markdown;
    private List<Comment> comments;
    private List<String> votesBy;
    private List<String> savedBy;

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public String getMarkdown() {
        return markdown;
    }
    public void setMarkdown(String markdown) {
        this.markdown = markdown;
    }

    public String getSummary() {
        return Summary;
    }

    public void setSummary(String summary) {
        Summary = summary;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

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
    public List<String> getVotesBy() {
        return votesBy;
    }

    public void setVotesBy(List<String> votesBy) {
        this.votesBy = votesBy;
    }

    public List<String> getSavedBy() {
        return savedBy;
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setSavedBy(List<String> savedBy) {
        this.savedBy = savedBy;
    }
}
