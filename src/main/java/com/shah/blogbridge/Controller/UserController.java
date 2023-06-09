package com.shah.blogbridge.Controller;

import com.shah.blogbridge.model.Comment;
import com.shah.blogbridge.model.Notification;
import com.shah.blogbridge.model.Post;
import com.shah.blogbridge.model.ResponseApi;
import com.shah.blogbridge.service.PostInteractionService;
import com.shah.blogbridge.service.PostService;
import com.shah.blogbridge.service.UserService;
import com.shah.blogbridge.user.UserDto;
import com.shah.blogbridge.user.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserService userService;
    private final PostService postService;
    private final PostInteractionService postInteractionService;

    public UserController(UserService userService, PostService postService, PostInteractionService postInteractionService) {
        this.userService = userService;
        this.postService = postService;
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
            @RequestParam("followingUserId") String followingUserId
    ) {
        return userService.followUser(userid, followingUserId);
    }

    @PutMapping("/unfollow/{userid}")
    public ResponseEntity<ResponseApi> unfollowUser(
            @PathVariable String userid,
            @RequestParam("followingUserId") String followingUserId
    ) {
        return userService.unfollowUser(userid, followingUserId);
    }

    @PutMapping("/vote/{postId}")
    public ResponseEntity<String> votePost(
            @PathVariable String postId,
            @RequestParam("userId") String userId
    ) {
        return postInteractionService.vote(postId, userId);
    }

    @PutMapping("/unvote/{postId}")
    public ResponseEntity<String> unVotePost(
            @PathVariable String postId,
            @RequestParam("userId") String userId
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

    @PostMapping("/list/add/{postId}")
    public ResponseApi addToList(
            @PathVariable String postId,
            @RequestParam("userId") String userId,
            @RequestParam(value = "listName", required = false) String listName
    ){
        return postInteractionService.savePostToUserList(userId,postId,listName);
    }

    @DeleteMapping("/list/delete/{postId}")
    public ResponseApi removeFromList(
            @PathVariable String postId,
            @RequestParam("userId") String userId,
            @RequestParam("listName") String listName
    ){
        return postInteractionService.unSavePostFromUserList(userId,postId,listName);
    }

    @DeleteMapping("/list/{listName}")
    public ResponseApi removeList(
            @PathVariable String listName,
            @RequestParam("userId") String userId
    ){
        return postInteractionService.deleteListFromUser(userId,listName);
    }

    @GetMapping("/myposts/{userId}")
    public ResponseEntity<List<Post>> getMyPosts(@PathVariable String userId){
        return postService.getMyPosts(userId);
    }

}
