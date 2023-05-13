package com.shah.blogbridge;

import com.shah.blogbridge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlogBridgeApplication {
	public static void main(String[] args) {
		SpringApplication.run(BlogBridgeApplication.class, args);
	}
}
