package com.shah.mediumbackendclone.service;

import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.model.User;
import com.shah.mediumbackendclone.repository.UserRepository;
import org.apache.el.util.ReflectionUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseApi saveUser(OAuth2User user) {
        String email = user.getAttribute("email");
        String name = user.getAttribute("name");

        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) {
            return new ResponseApi(
                    "User already exits",
                    false
            );
        } else {
            User user1 = new User();
            user1.setEmail(email);
            user1.setName(name);
            userRepository.save(user1);
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

    public ResponseEntity<User> getUser(String userId) {
        Optional<User> existingUser = userRepository.findById(userId);
        //return existingUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        //the below has better readability
        if (existingUser.isPresent()) {
            return ResponseEntity.ok(existingUser.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> updateUser(
            String userId,
            String name,
            String bio,
            String avatar
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
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }

    }

}
