package com.example.user_service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class UserServiceApplicationTests {

	@Autowired
	PasswordEncoder encoder;

	@Test
	void contextLoads() {
	}

	@Test
	void testPassword() {
		String raw = "12345";
		String encoded = "$2a$10$LIg/XzV0yTl.GRdLzNULsuHgJR8JhEu5KaCIV1JJVXcMLfm4.SC6G"; // el de la DB

		System.out.println("matches: " + encoder.matches(raw, encoded));
	}

}
