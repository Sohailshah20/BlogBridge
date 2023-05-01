package com.shah.mediumbackendclone.service;

import com.shah.mediumbackendclone.user.UserDto;
import com.shah.mediumbackendclone.model.Notification;
import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.user.User;
import com.shah.mediumbackendclone.user.UserDtoMapper;
import com.shah.mediumbackendclone.user.UserList;
import com.shah.mediumbackendclone.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public ResponseEntity<UserDto> getUser(String userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            Optional<User> user = userRepository.findById(userId);
            return ResponseEntity.ok().body(UserDtoMapper.toDto(user.get()));
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
        Optional<User> existingUser = userRepository.findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();

            String name = userDto.name();
            if (name != null && !name.isEmpty()) {
                user.setName(name);
            }

            String bio = userDto.bio();
            if (bio != null && !bio.isEmpty()) {
                user.setBio(bio);
            }

            String avatar = userDto.avatar();
            if (avatar != null && !avatar.isEmpty()) {
                user.setAvatar(avatar);
            }

            userRepository.save(user);
            return ResponseEntity.ok(UserDtoMapper.toDto(user));
        } else {
            return ResponseEntity.notFound().build();
        }

    }

    public ResponseEntity<List<String>> getFollowings(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<String> following = user.get().getFollowing();
            return ResponseEntity.ok(following);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<String>> getFollowers(String userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<String> followers = user.get().getFollowers();
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

    public ResponseEntity<List<String>> deleteUserInterest(String userId, String interest) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<String> updatedInterests = user.get().getIntrests().stream()
                    .filter(str -> !str.equals(interest))
                    .toList();
            user.get().setIntrests(updatedInterests);
            userRepository.save(user.get());
            return ResponseEntity.ok().body(updatedInterests);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ResponseApi> followUser(String userId, String followingUserId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<String> following = user.get().getFollowing();
            following.add(followingUserId);
            user.get().setFollowing(following);
            userRepository.save(user.get());
            return ResponseEntity.ok(new ResponseApi(
                    "user followed",
                    true
            ));
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<ResponseApi> unfollowUser(String userId, String followingUserId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            List<String> following = user.get().getFollowing();
            following.remove(followingUserId);
            user.get().setFollowing(following);
            userRepository.save(user.get());
            return ResponseEntity.ok(new ResponseApi(
                    "user unfollowed",
                    true
            ));
        }else {
            return ResponseEntity.notFound().build();
        }
    }



}
