package com.shah.blogbridge.Controller;

import com.shah.blogbridge.model.Post;
import com.shah.blogbridge.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getExplorePosts(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "userId") String userId
    ) {
        return homeService.getPostsForExplore(pageNumber, userId);

    }

    @GetMapping("/gettoppicks")
    public ResponseEntity<List<Post>> getTopPicks() {
        return homeService.getTopPicks();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getMoreFromUser(
            String userId,
            @RequestBody String postId) {
        return homeService.getMoreFromUser(userId, postId);
    }
}
