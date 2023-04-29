package com.shah.mediumbackendclone.model;

import org.bson.codecs.pojo.annotations.BsonId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Document(collection = "user")
public class User{
    private String id;
    private String name;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String bio;
    private String avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String intrests;
    private List<User> mutedAuthors;
    private List<User> followers;
    private List<User> following;
    private List<UserList> lists;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getIntrests() {
        return intrests;
    }

    public void setIntrests(String intrests) {
        this.intrests = intrests;
    }

    public List<User> getMutedAuthors() {
        return mutedAuthors;
    }

    public void setMutedAuthors(List<User> mutedAuthors) {
        this.mutedAuthors = mutedAuthors;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<UserList> getLists() {
        return lists;
    }

    public void setLists(List<UserList> lists) {
        this.lists = lists;
    }

}
