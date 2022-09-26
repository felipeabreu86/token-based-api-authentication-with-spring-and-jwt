package com.example.token;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {"API_JWT_SECRET = api_jwt_secret"})
class TokenApplicationTests {

	@Test
	void contextLoads() {
	}

}
