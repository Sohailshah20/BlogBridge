package com.shah.mediumbackendclone.Controller;

import com.shah.mediumbackendclone.model.Post;
import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.service.PostService;
import com.shah.mediumbackendclone.user.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPost(@PathVariable String postId){
        return postService.getPost(postId);
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Post> createNewPost(
            @PathVariable String userId,
            @RequestBody Post post
    ){
        return postService.savePost(userId,post);
    }

    @PutMapping("/update/{{userId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable String userId,
            @RequestBody Post post
    ){
        return postService.updatePost(userId,post);
    }

    @DeleteMapping("/delete/{userId}/{postId}")
    public ResponseApi deletePost(
            @PathVariable String userId,
            @PathVariable String postId
    ){
        return postService.deletePost(postId,userId);
    }

    @GetMapping("/lists/{userId}")
    public ResponseEntity<List<UserList>> getSavedPosts(
            @PathVariable String userId
    ){
        return postService.getAllSavedLists(userId);
    }
}
