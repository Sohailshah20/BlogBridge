package com.shah.blogbridge.repository;

import com.shah.blogbridge.model.Post;
import org.springdoc.core.converters.models.Sort;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {

    List<Post> findAllByOwnerId(String ownerId);

    List<Post> findTop3ByOwnerId(String ownerId);

    List<Post> findTop3ByOrderByVoteCountDesc();

}
