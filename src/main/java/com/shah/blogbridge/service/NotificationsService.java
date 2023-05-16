package com.shah.blogbridge.service;

import com.shah.blogbridge.model.Notification;
import com.shah.blogbridge.repository.NotificationsRepository;

public class NotificationsService {

    private final NotificationsRepository repository;

    public NotificationsService(NotificationsRepository repository) {
        this.repository = repository;
    }

    private void saveNotification(Notification notification){
        repository.save(notification);
    }

    public void addVoteNotification(String postId, String userId, String name, String avatar, String title) {
        Notification voteNotification = new Notification();
        voteNotification.setUserId(userId);
        voteNotification.setUsername(name);
        voteNotification.setAvatar(avatar);
        voteNotification.setPostId(postId);
        voteNotification.setPostTitle(title);
        voteNotification.setMessage(name +" liked your post "+ title);
        saveNotification(voteNotification);
    }

    public void addCommentNotification(String postId, String userId, String name, String avatar, String title, String comment) {
        Notification voteNotification = new Notification();
        voteNotification.setUserId(userId);
        voteNotification.setUsername(name);
        voteNotification.setAvatar(avatar);
        voteNotification.setPostId(postId);
        voteNotification.setPostTitle(title);
        voteNotification.setMessage(name +" commented "+ comment +" on your post "+ title);
        saveNotification(voteNotification);
    }

    public void addFollowNotification( String userId, String name, String avatar) {
        Notification voteNotification = new Notification();
        voteNotification.setUserId(userId);
        voteNotification.setUsername(name);
        voteNotification.setAvatar(avatar);
        voteNotification.setMessage(name +" followed you");
        saveNotification(voteNotification);
    }
}
