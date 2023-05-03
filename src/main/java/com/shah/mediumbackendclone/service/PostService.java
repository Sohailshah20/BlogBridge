package com.shah.mediumbackendclone.service;

import com.shah.mediumbackendclone.model.Post;
import com.shah.mediumbackendclone.model.ResponseApi;
import com.shah.mediumbackendclone.repository.PostRepository;
import com.shah.mediumbackendclone.repository.UserRepository;
import com.shah.mediumbackendclone.user.User;
import com.shah.mediumbackendclone.user.UserList;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

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

    public ResponseEntity<Post> savePost(String userId, Post post) {
        Post newPost = new Post();
        newPost.setOwnerId(userId);

        String title = post.getTitle();
        if (title != null && !title.isEmpty()) {
            newPost.setTitle(title);
        } else {
            return ResponseEntity.badRequest().build();
        }

        String content = post.getContent();
        if (content != null && !content.isEmpty()) {
            newPost.setContent(content);
        } else {
            return ResponseEntity.badRequest().build();
        }
        //image can be null
        String image = post.getImage();

        postRepository.save(newPost);
        return ResponseEntity.ok().body(newPost);
    }

    public ResponseEntity<Post> updatePost(String userId, Post post) {
        Optional<Post> existingPost = postRepository
                .findById(post.getPostId());
        if (existingPost.isPresent()) {
            if (!existingPost.get().getOwnerId().equals(userId)) {
                ResponseEntity.badRequest().build();
            }
            Post structuredPost = structurePost(post);
            return savePost(userId, structuredPost);
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

    public ResponseEntity<List<UserList>> getAllSavedLists(String userid){
        User userPresent = userService.isUserPresent(userid);
        if (userPresent != null){
            return ResponseEntity.ok(userPresent.getLists());
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    //This function does not special
    //It just sets the image and summary in the post
    private Post structurePost(Post post) {
        String test = post.getMarkdown() != null ? post.getMarkdown() : "";
        Pattern codeRegex = Pattern.compile("<code>(.*?)<\\/code>", Pattern.DOTALL);
        String withoutCode = codeRegex.matcher(test).replaceAll("");
        Pattern imgRegex = Pattern.compile("<img.*?src=['\"](.*?)['\"]", Pattern.DOTALL);
        Matcher matcher = imgRegex.matcher(test);
        String imgUrl = matcher.find() ? matcher.group(1) : null;
        Pattern htmlRegexG = Pattern.compile("<(?:\"[^\"]*\"['\"]*|'[^']*'['\"]*|[^'\">])+>", Pattern.DOTALL);
        String summary = htmlRegexG.matcher(withoutCode).replaceAll("");
        post.setImage(imgUrl);
        post.setSummary(summary);
        return post;
    }

}
