package com.shah.mediumbackendclone.service;

import com.shah.mediumbackendclone.model.Notification;
import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.model.User;
import com.shah.mediumbackendclone.model.UserList;
import com.shah.mediumbackendclone.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseApi saveUser(User user) {
        String email = user.getEmail();
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return new ResponseApi(
                    "User already exits",
                    false
            );
        } else {
            userRepository.save(user);
            return new ResponseApi(
                    "User saved to database",
                    true
            );
        }
    }

    public ResponseApi deleteUser(String userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            try {

                userRepository.deleteById(userId);
                return new ResponseApi(
                        "User deleted successfully",
                        true
                );
            } catch (Exception e) {
                return new ResponseApi(
                        e.getMessage(),
                        false
                );
            }

        } else {
            return new ResponseApi(
                    "User doesn't exists",
                    false
            );
        }
    }

    public User getUser(String userId) {
        return userRepository.findById(userId).get();
        //return existingUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //the below has better readability

    }

    public ResponseEntity<User> updateUser(
            String userId,
            String name,
            String bio,
            String avatar,
            List<String> interests
    ) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            if (StringUtils.hasText(name)) {
                user.setName(name);
            }
            if (StringUtils.hasText(bio)) {
                user.setName(bio);
            }
            if (StringUtils.hasText(avatar)) {
                user.setName(avatar);
            }
            if (!interests.isEmpty()){
                user.setIntrests(interests);
            }
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<List<User>> getFollowings(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<User> following = user.get().getFollowing();
            return ResponseEntity.ok(following);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<User>> getFollowers(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<User> followers = user.get().getFollowers();
            return ResponseEntity.ok(followers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Notification>> getNotifications(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<Notification> notifications = user.get().getNotifications();
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<String>> getInterest(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<String> interest = user.get().getIntrests();
            return ResponseEntity.ok(interest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<UserList>> getUserLists(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<UserList> userLists = user.get().getLists();
            return ResponseEntity.ok(userLists);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> deleteUserInterest(String userId, String interest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<String> updatedInterests = user.get().getIntrests().stream()
                    .filter(str -> !str.equals(interest))
                    .toList();
            ResponseEntity<User> updateUser = updateUser(
                    userId,
                    user.get().getName(),
                    user.get().getBio(),
                    user.get().getAvatar(),
                    updatedInterests
            );
            return ResponseEntity.ok(updateUser.getBody());
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
