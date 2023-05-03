package com.shah.mediumbackendclone.Controller;

import com.shah.mediumbackendclone.model.Comment;
import com.shah.mediumbackendclone.model.Notification;
import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.service.PostInteractionService;
import com.shah.mediumbackendclone.service.UserService;
import com.shah.mediumbackendclone.user.UserDto;
import com.shah.mediumbackendclone.user.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final PostInteractionService postInteractionService;

    public UserController(UserService userService, PostInteractionService postInteractionService) {
        this.userService = userService;
        this.postInteractionService = postInteractionService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/update/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String userId,
            @RequestBody UserDto userDto
    ) {
        return userService.updateUser(userId, userDto);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<String>> getFollowings(@PathVariable String userId) {
        return userService.getFollowings(userId);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<String>> getFollowers(@PathVariable String userId) {
        return userService.getFollowers(userId);
    }

    @GetMapping("/notifications/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable String userId) {
        return userService.getNotifications(userId);
    }

    @GetMapping("/interest/{userId}")
    public ResponseEntity<List<String>> getInterest(@PathVariable String userId) {
        return userService.getInterest(userId);
    }

    @GetMapping("/lists/{userId}")
    public ResponseEntity<List<UserList>> getUserLists(@PathVariable String userId) {
        return userService.getUserLists(userId);
    }

    @DeleteMapping("/delete/userinterest/{userid}")
    public ResponseEntity<List<String>> deleteUserInterest(
            @PathVariable String userId,
            @RequestBody String interest
    ) {
        return userService.deleteUserInterest(userId, interest);
    }

    @DeleteMapping("/{userId}")
    public ResponseApi deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

    @PutMapping("/follow/{userid}")
    public ResponseEntity<ResponseApi> followUser(
            @PathVariable String userid,
            @RequestBody String followingUserId
    ) {
        return userService.followUser(userid, followingUserId);
    }

    @PutMapping("/unfollow/{userid}")
    public ResponseEntity<ResponseApi> unfollowUser(
            @PathVariable String userid,
            @RequestBody String followingUserId
    ) {
        return userService.unfollowUser(userid, followingUserId);
    }

    @PutMapping("/vote/{userId}/{postId}")
    public ResponseEntity<Integer> votePost(
            @PathVariable String userId,
            @PathVariable String postId
    ) {
        return postInteractionService.vote(postId, userId);
    }

    @PutMapping("/unvote/{userId}/{postId}")
    public ResponseEntity<Integer> unVotePost(
            @PathVariable String userId,
            @PathVariable String postId
    ) {
        return postInteractionService.unVote(postId, userId);
    }

    @GetMapping("/getcomments/{postId}")
    public ResponseEntity<List<Comment>> getComments(
            @PathVariable String postId
    ) {
        return postInteractionService.getComments(postId);
    }

    @PostMapping("/comment/{postId}")
    public ResponseEntity<List<Comment>> addComments(
            @PathVariable String postId,
            @RequestBody Comment comment
    ) {
        return postInteractionService.putComment(postId,comment);
    }

    @PostMapping("/list/add/{userId}/{postId}")
    public ResponseApi addToList(
            @PathVariable String postId,
            @PathVariable String userIdId,
            @RequestBody String listName
    ){
        return postInteractionService.savePostToUserList(userIdId,postId,listName);
    }

    @PutMapping("/list/remove/{userId}/{postId}")
    public ResponseApi removeFromList(
            @PathVariable String postId,
            @PathVariable String userIdId,
            @RequestBody String listName
    ){
        return postInteractionService.unSavePostFromUserList(userIdId,postId,listName);
    }



}
