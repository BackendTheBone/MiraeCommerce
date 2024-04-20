package com.mirae.commerce.mytest;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public int testDivide(int arg) {
        return 987654321 / arg;
    }
}
