package com.shah.mediumbackendclone;

import com.shah.mediumbackendclone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MediumBackendCloneApplication  {
	@Autowired UserRepository userService;

	public static void main(String[] args) {
		SpringApplication.run(MediumBackendCloneApplication.class, args);
	}
}
