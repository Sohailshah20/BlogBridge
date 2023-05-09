package com.shah.blogbridge.service;

import com.shah.blogbridge.model.Comment;
import com.shah.blogbridge.model.Post;
import com.shah.blogbridge.model.ResponseApi;
import com.shah.blogbridge.repository.PostRepository;
import com.shah.blogbridge.user.User;
import com.shah.blogbridge.user.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostInteractionService {

    private final PostService postService;

    private final PostRepository postRepository;
    private final UserService userService;

    public PostInteractionService(PostService postService, PostRepository postRepository, UserService userService) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public ResponseEntity<String> vote(String postId, String userId) {
        Post post = postService.getPost(postId).getBody();
        User user = userService.isUserPresent(userId);
        if (user != null && post != null) {
            List<String> votesBy = post.getVotesBy();
            if (votesBy == null) {
                votesBy = new ArrayList<>();
            }
            if (!votesBy.contains(userId)) {
                votesBy.add(userId);
                post.setVoteCount(votesBy.size());
                post.setVotesBy(votesBy);
                postRepository.save(post);
                return ResponseEntity.ok(String.valueOf(post.getVoteCount()));
            }else {
                return ResponseEntity.badRequest().body("user has already liked the post");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<String> unVote(String postId, String userId) {
        Post post = postService.getPost(postId).getBody();
        User user = userService.isUserPresent(userId);
        if (user != null && post != null) {
            List<String> votesBy = post.getVotesBy();
            if (votesBy.contains(userId)) {
                votesBy.remove(userId);
                post.setVoteCount(votesBy.size());
                post.setVotesBy(votesBy);
                postRepository.save(post);
                return ResponseEntity.ok(String.valueOf(post.getVoteCount()));
            } else {
                return ResponseEntity.notFound().build();

            }
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
            if (comments == null){
                comments = new ArrayList<>();
            }
            comment.setTimeStamp(LocalDateTime.now());
            comments.add(comment);
            post.setComments(comments);
            postRepository.save(post);
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
        } else {
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
        } else {
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


    //suggest topics
    //get topic posts

}
