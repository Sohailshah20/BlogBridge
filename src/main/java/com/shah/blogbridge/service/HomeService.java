package com.shah.blogbridge.service;

import com.shah.blogbridge.model.Post;
import com.shah.blogbridge.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HomeService {
    private final PostRepository postRepository;

    public HomeService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public ResponseEntity<List<Post>> getPostsForExplore(Integer pageNumber,String userId) {
        Pageable pageable = PageRequest.of(pageNumber,5);
        Page<Post> allPosts = postRepository.findAll(pageable);
        List<Post> posts = allPosts.getContent().stream()
                .filter(post -> !post.getOwnerId().equals(userId))
                .collect(Collectors.toList());
        return ResponseEntity.ok(posts);
    }

    public ResponseEntity<List<Post>> getTopPicks() {
        Pageable pageable = PageRequest.of(0,3);
        List<Post> topPosts = postRepository.findTop3ByOrderByVoteCountDesc();
        return ResponseEntity.ok(topPosts);
    }

    public ResponseEntity<List<Post>> getMoreFromUser(String userId,String postId) {
        List<Post> allPosts = postRepository.findTop3ByOwnerId(userId);
        List<Post> posts = allPosts.stream()
                .filter(post -> !post.getPostId().equals(postId))
                .toList();
        return ResponseEntity.ok(posts);


    }

}
