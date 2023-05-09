package com.shah.blogbridge.Controller;

import com.shah.blogbridge.model.Post;
import com.shah.blogbridge.model.PostUpdateRequest;
import com.shah.blogbridge.model.ResponseApi;
import com.shah.blogbridge.service.PostService;
import com.shah.blogbridge.user.UserList;
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
            @RequestParam("title") String title,
            @RequestParam("tags") String tags,
            @RequestParam("markdown") String markdown
    ){
        return postService.savePost(userId,title,tags,markdown);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<Post> updatePost(
            @PathVariable String userId,
            @RequestBody PostUpdateRequest post
    ){
        return postService.updatePost(userId,post);
    }

    @DeleteMapping("/delete/{postId}")
    public ResponseApi deletePost(
            @PathVariable String postId,
            @RequestParam("userId") String userId
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
