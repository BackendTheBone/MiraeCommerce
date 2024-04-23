package com.mirae.commerce;

import com.mirae.commerce.auth.jwt.Jwt;
import com.mirae.commerce.auth.jwt.JwtProvider;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MiraeCommerceApplicationTests {

	private final JwtProvider jwtProvider;

	@Autowired
	MiraeCommerceApplicationTests(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Test
	@DisplayName("JWT 토큰 테스트")
	void JwtTest() {
		Map<String, Object> claims = new HashMap<>();
		String userEmailKey = "email";
		String userEmail = "woals1488@naver.com";
		claims.put(userEmailKey, userEmail);

		Jwt token = jwtProvider.createJwt(claims);
		Claims claimsResult = jwtProvider.getClaims(token.getAccessToken());

		assertThat(claimsResult.get(userEmailKey)).isEqualTo(userEmail);

		for (String key : claimsResult.keySet()) {
			System.out.println("key = " + key + ", val = " + claimsResult.get(key));
		}
	}

	@Test
	@DisplayName("액세스 토큰 만료 테스트")
	void accessTokenExpirationTest() {

	}

}
