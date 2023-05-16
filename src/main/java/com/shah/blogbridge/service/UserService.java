package com.shah.blogbridge.service;

import com.shah.blogbridge.model.Notification;
import com.shah.blogbridge.model.ResponseApi;
import com.shah.blogbridge.repository.UserRepository;
import com.shah.blogbridge.user.User;
import com.shah.blogbridge.user.UserDto;
import com.shah.blogbridge.user.UserDtoMapper;
import com.shah.blogbridge.user.UserList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);


    private final UserRepository userRepository;
    private final NotificationsService notificationsService;

    public UserService(UserRepository userRepository, NotificationsService notificationsService) {
        this.userRepository = userRepository;
        this.notificationsService = notificationsService;
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
        User user = isUserPresent(userId);
        if (user != null) {
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

    public ResponseEntity<UserDto> getUser(String userId) {
        User user = isUserPresent(userId);
        if (user != null) {
            return ResponseEntity.ok().body(UserDtoMapper.toDto(user));
        } else {
            return ResponseEntity.notFound().build();
        }
        //return existingUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //the below has better readability

    }

    public ResponseEntity<UserDto> updateUser(
            String userId,
            UserDto userDto
//            List<String> interests
    ) {
        User existingUser = isUserPresent(userId);
        if (existingUser != null) {

            String name = userDto.name();
            if (name != null && !name.isEmpty()) {
                existingUser.setName(name);
            }

            String bio = userDto.bio();
            if (bio != null && !bio.isEmpty()) {
                existingUser.setBio(bio);
            }

            String avatar = userDto.avatar();
            if (avatar != null && !avatar.isEmpty()) {
                existingUser.setAvatar(avatar);
            }

            userRepository.save(existingUser);
            return ResponseEntity.ok(UserDtoMapper.toDto(existingUser));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<List<String>> getFollowings(String userId) {
        User user = isUserPresent(userId);
        if (user != null) {
            List<String> following = user.getFollowing();
            return ResponseEntity.ok(following);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<String>> getFollowers(String userId) {
        User user = isUserPresent(userId);
        if (user != null) {
            List<String> followers = user.getFollowers();
            return ResponseEntity.ok(followers);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Notification>> getNotifications(String userId) {
        User user = isUserPresent(userId);
        if (user != null) {
            List<Notification> notifications = user.getNotifications();
            return ResponseEntity.ok(notifications);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<String>> getInterest(String userId) {
        User user = isUserPresent(userId);
        if (user != null) {
            List<String> interest = user.getIntrests();
            return ResponseEntity.ok(interest);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<UserList>> getUserLists(String userId) {
        User user = isUserPresent(userId);
        if (user != null) {
            List<UserList> userLists = user.getLists();
            return ResponseEntity.ok(userLists);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<String>> deleteUserInterest(String userId, String interest) {
        User user = isUserPresent(userId);
        if (user != null) {
            List<String> updatedInterests = user.getIntrests().stream()
                    .filter(str -> !str.equals(interest))
                    .toList();
            user.setIntrests(updatedInterests);
            userRepository.save(user);
            return ResponseEntity.ok().body(updatedInterests);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ResponseApi> followUser(String userId, String followingUserId) {
        logger.trace(followingUserId.toString());
        User user = isUserPresent(userId);
        if (user != null) {
            List<String> following = user.getFollowing();
            if (following == null){
                following = new ArrayList<>();
            }
            following.add(followingUserId);
            user.setFollowing(following);
            userRepository.save(user);
            notificationsService.addFollowNotification(userId,user.getId(),user.getAvatar());
            return ResponseEntity.ok(new ResponseApi(
                    "user followed",
                    true
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ResponseApi> unfollowUser(String userId, String followingUserId) {
        User user = isUserPresent(userId);
        if (user != null) {
            List<String> following = user.getFollowing();
            following.remove(followingUserId);
            user.setFollowing(following);
            userRepository.save(user);
            return ResponseEntity.ok(new ResponseApi(
                    "user unfollowed",
                    true
            ));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseApi updateUserLists(User user) {
        try {
            userRepository.save(user);
            return new ResponseApi(
                    "user list updated",
                    true
            );
        }catch (Exception e){
            return new ResponseApi(
                    e.getMessage(),
                    false
            );
        }

    }

    public User isUserPresent(String userId){
        Optional<User> user = userRepository.findById(userId);
        return user.orElse(null);
    }


}
