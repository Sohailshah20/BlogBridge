package com.shah.blogbridge.repository;

import com.shah.blogbridge.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {
    List<Post> findAllByOwnerIdOrderByTimeStampDesc(String ownerId);

    List<Post> findByOwnerIdOrderByTimeStampDesc(String ownerId, Pageable pageable);

    List<Post> findTop3ByOrderByVoteCountDesc();

}
