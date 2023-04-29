package com.shah.mediumbackendclone;

import com.shah.mediumbackendclone.model.User;
import com.shah.mediumbackendclone.repository.UserRepository;
import com.shah.mediumbackendclone.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.core.user.OAuth2User;

@SpringBootApplication
public class MediumBackendCloneApplication implements CommandLineRunner {
	@Autowired UserRepository userService;

	public static void main(String[] args) {



		SpringApplication.run(MediumBackendCloneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setName("sohail");
		userService.save(user);
	}
}
