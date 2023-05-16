package com.shah.blogbridge.service;

import com.shah.blogbridge.model.Post;
import com.shah.blogbridge.model.PostUpdateRequest;
import com.shah.blogbridge.model.ResponseApi;
import com.shah.blogbridge.repository.PostRepository;
import com.shah.blogbridge.repository.UserRepository;
import com.shah.blogbridge.user.User;
import com.shah.blogbridge.user.UserList;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserRepository userRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public ResponseEntity<Post> getPost(String postId) {
        Optional<Post> post = postRepository.findById(postId);
        if (post.isPresent()) {
            return ResponseEntity.ok().body(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<List<Post>> getMyPosts(String userId) {
        List<Post> posts = postRepository.findAllByOwnerId(userId);
        if (!posts.isEmpty()) {
            List<Post> sortedPosts = posts.stream()
                    .sorted(Comparator.comparing(Post::getTimeStamp).reversed())
                    .toList();
            return ResponseEntity.ok(sortedPosts);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<Post> savePost(String userId, String title, String tags, String markdown) {
        Post newPost = new Post();
        newPost.setOwnerId(userId);
        if (title != null && !title.isEmpty()) {
            newPost.setTitle(title);
        } else {
            return ResponseEntity.badRequest().build();
        }
        if (tags != null && !tags.isEmpty()) {
            List<String> tagsList = Arrays.stream(tags.split(",")).toList();
            newPost.setTags(tagsList);
        }
        if (markdown != null && !markdown.isEmpty()) {
            newPost.setMarkdown(markdown);
            Map<String, String> stringStringMap = structurePost(markdown);
            newPost.setImage(stringStringMap.get("imgUri"));
            newPost.setSummary(stringStringMap.get("summary"));
        }
        newPost.setTimeStamp(LocalDateTime.now());
        postRepository.save(newPost);
        return ResponseEntity.ok().body(newPost);
    }

    public ResponseEntity<Post> updatePost(String userId, PostUpdateRequest post) {
        Optional<Post> existingPost = postRepository
                .findById(post.getPostId());
        if (existingPost.isPresent()) {
            if (!existingPost.get().getOwnerId().equals(userId)) {
                return ResponseEntity.badRequest().build();
            }
            String tags = post.getTags();
            if (tags != null && !tags.isEmpty()) {
                List<String> tagsList = Arrays.stream(tags.split(",")).toList();
                existingPost.get().setTags(tagsList);
            }
            Map<String, String> stringStringMap = structurePost(post.getMarkdown());
            existingPost.get().setTitle(post.getTitle());
            existingPost.get().setImage(stringStringMap.get("imgUri"));
            existingPost.get().setSummary(stringStringMap.get("summary"));
            postRepository.save(existingPost.get());
            return ResponseEntity.ok(existingPost.get());
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseApi deletePost(String postId, String userId) {
        Optional<Post> existingPost = postRepository
                .findById(postId);
        if (existingPost.isPresent()) {
            if (!existingPost.get().getOwnerId().equals(userId)) {
                return new ResponseApi(
                        "not authorized to delete the post",
                        false
                );
            }
            postRepository.deleteById(postId);
            return new ResponseApi(
                    "Post deleted successfully",
                    true
            );
        } else {
            return new ResponseApi(
                    "post doesn't exist",
                    false
            );
        }

    }

    public ResponseEntity<List<UserList>> getAllSavedLists(String userid) {
        User userPresent = userService.isUserPresent(userid);
        if (userPresent != null) {
            return ResponseEntity.ok(userPresent.getLists());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //This function does not special
    //It just sets the image and summary in the post
    private Map<String, String> structurePost(String markdwon) {
        Map<String, String> map = new HashMap<>();
        //Image matcher
        Pattern imgRegex = Pattern.compile("<img\\\\s+src=\"([^\"]+)\"\\\\s*/>");
        Matcher matcher = imgRegex.matcher(markdwon);
        String imgUrl = matcher.find() ? matcher.group(1) : null;
        map.put("imageUri", imgUrl);

        //summary matcher
        Pattern codeRegex = Pattern.compile("<code>(.*?)</code>", Pattern.DOTALL);
        String withoutCode = codeRegex.matcher(markdwon).replaceAll("");
        Pattern htmlRegexG = Pattern.compile("<(?:\"[^\"]*\"['\"]*|'[^']*'['\"]*|[^'\">])+>", Pattern.DOTALL);
        String summary = htmlRegexG.matcher(withoutCode).replaceAll("");


        map.put("summary", summary);
        return map;
    }

}
