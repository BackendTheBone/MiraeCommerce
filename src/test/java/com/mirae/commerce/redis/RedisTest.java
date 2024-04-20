package com.mirae.commerce.redis;


import com.mirae.commerce.auth.redis.RedisRefreshTokenManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {
    @Autowired private RedisRefreshTokenManager redisRefreshTokenManager;

    @Test
    @DisplayName("레디스 키-값 삽입, 삭제")
    public void redisKeyValueCreateRemoveTest() {
        String key = "key1";
        String val = "val1";
        redisRefreshTokenManager.putRefreshToken(key, val);
        String resultVal = redisRefreshTokenManager.getRefreshToken(key);
        Assertions.assertThat(resultVal).isEqualTo(key);
    }
}
