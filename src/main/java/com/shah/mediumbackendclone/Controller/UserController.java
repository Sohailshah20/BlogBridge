package com.shah.mediumbackendclone.Controller;

import com.shah.mediumbackendclone.model.Notification;
import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.model.User;
import com.shah.mediumbackendclone.model.UserList;
import com.shah.mediumbackendclone.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/user", produces = "application/json")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<User>> getFollowings(@PathVariable String userId) {
        return userService.getFollowings(userId);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<User>> getFollowers(@PathVariable String userId) {
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

    @GetMapping("/userlists/{userId}")
    public ResponseEntity<List<UserList>> getUserLists(@PathVariable String userId) {
        return userService.getUserLists(userId);
    }

    @DeleteMapping("/delete/userinterest/{userid}")
    public ResponseEntity<User> deleteUserInterest(
            @PathVariable String userId,
            @RequestBody String interest
    ) {
        return userService.deleteUserInterest(userId, interest);
    }

    @DeleteMapping("/{userId}")
    public ResponseApi deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);
    }

}
