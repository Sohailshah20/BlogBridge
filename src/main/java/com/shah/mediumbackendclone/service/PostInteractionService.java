package com.shah.mediumbackendclone.service;

import com.shah.mediumbackendclone.model.Comment;
import com.shah.mediumbackendclone.model.Post;
import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.user.User;
import com.shah.mediumbackendclone.user.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostInteractionService {

    private final PostService postService;
    private final UserService userService;

    public PostInteractionService(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    public ResponseEntity<Integer> vote(String postId, String userId) {
        Post post = postService.getPost(postId).getBody();
        User user = userService.isUserPresent(userId);
        if (user != null && post != null) {
            List<String> votesBy = post.getVotesBy();
            votesBy.add(userId);
            post.setVotesBy(votesBy);
            postService.updatePost(post.getOwnerId(), post);
            return ResponseEntity.ok(post.getVoteCount());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Integer> unVote(String postId, String userId) {
        Post post = postService.getPost(postId).getBody();
        User user = userService.isUserPresent(userId);
        if (user != null && post != null) {
            List<String> votesBy = post.getVotesBy();
            votesBy.remove(userId);
            post.setVotesBy(votesBy);
            postService.updatePost(post.getOwnerId(), post);
            return ResponseEntity.ok(post.getVoteCount());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Comment>> getComments(String postId) {
        Post post = postService.getPost(postId).getBody();
        if (post != null) {
            return ResponseEntity.ok(post.getComments());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Comment>> putComment(String postId, Comment comment) {
        Post post = postService.getPost(postId).getBody();
        User user = userService.isUserPresent(comment.getUserId());
        if (post != null && user != null) {
            List<Comment> comments = post.getComments();
            comment.setTimeStamp(LocalDateTime.now());
            comments.add(comment);
            post.setComments(comments);
           postService.updatePost(post.getOwnerId(), post);
            return ResponseEntity.ok(comments);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseApi savePostToUserList(String userId, String postId, String listName) {
        Post existingPost = postService.getPost(postId).getBody();
        User user = userService.isUserPresent(userId);
        if (existingPost != null && user != null) {
            List<UserList> userLists = userService.getUserLists(userId).getBody();
            if (userLists != null && !userLists.isEmpty()) {
                UserList userlist = userLists.stream().filter(ul -> ul.getName().equals(listName)).findFirst().get();
                List<String> posts = userlist.getPostId();
                posts.add(postId);
                userLists.add(userlist);
                user.setLists(userLists);
                return userService.updateUserLists(user);
            }
        }else {
            return new ResponseApi(
                    "Can't save the post right now",
                    false
            );
        }
        return new ResponseApi(
                "Can't save the post right now",
                false
        );
    }

    public ResponseApi unSavePostFromUserList(String userId, String postId, String listName) {
        Post existingPost = postService.getPost(postId).getBody();
        User user = userService.isUserPresent(userId);
        if (existingPost != null && user != null) {
            List<UserList> userLists = userService.getUserLists(userId).getBody();
            if (userLists != null && !userLists.isEmpty()) {
                UserList userlist = userLists.stream().filter(ul -> ul.getName().equals(listName)).findFirst().get();
                List<String> posts = userlist.getPostId();
                posts.remove(postId);
                userLists.add(userlist);
                user.setLists(userLists);
                return userService.updateUserLists(user);
            }
        }else {
            return new ResponseApi(
                    "Can't save the post right now",
                    false
            );
        }
        return new ResponseApi(
                "Can't save the post right now",
                false
        );
    }


    //explore
    //morefrom
    //suggest posts
    //suggest topics
    //get topic posts
    //home posts
    //get user posts

}
