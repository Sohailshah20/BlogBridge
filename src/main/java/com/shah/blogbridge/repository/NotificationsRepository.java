package com.shah.blogbridge.repository;

import com.shah.blogbridge.model.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  NotificationsRepository extends MongoRepository<Notification, String> {

}
