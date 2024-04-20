package com.mirae.commerce.mytest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    @GetMapping("/exception1")
    ResponseEntity<Integer> testException1(@RequestParam("arg") int arg) {
        // 컨트롤러에서 예외 발생
        // int num = 123456789 / arg;
        
        int num = testService.testDivide(arg);

        return new ResponseEntity<>(
                num,
                HttpStatus.OK
        );
    }
}
