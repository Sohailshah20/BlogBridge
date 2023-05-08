package com.shah.blogbridge.Controller;

import com.shah.blogbridge.model.Post;
import com.shah.blogbridge.service.HomeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class HomeController {

    private final HomeService homeService;

    public HomeController(HomeService homeService) {
        this.homeService = homeService;
    }

    @GetMapping
    public ResponseEntity<List<Post>> getExplorePosts(Integer pageNumber,
                                                      Integer pageSize,
                                                      String userId
    ) {
        return homeService.getPostsForExplore(pageNumber,pageSize,userId);

    }

    @GetMapping("/gettoppicks")
    public ResponseEntity<List<Post>> getTopPicks(){
        return homeService.getTopPicks();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Post>> getMoreFromUser(
            String userId,
            @RequestBody String postId){
        return homeService.getMoreFromUser(userId,postId);
    }
}
