package com.shah.mediumbackendclone;

import com.shah.mediumbackendclone.model.User;
import com.shah.mediumbackendclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediumBackendCloneApplication implements CommandLineRunner {

	@Autowired
	UserRepository repo;

	public static void main(String[] args) {
		SpringApplication.run(MediumBackendCloneApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		User user = new User();
		user.setName("Sohail Shah");
		repo.save(user);
	}
}
