package com.mirae.commerce.member.controller;
import com.mirae.commerce.auth.jwt.JwtRequired;
import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.service.MemberService;
import com.mirae.commerce.member.dto.ConfirmEmailRequest;
import com.mirae.commerce.member.dto.ModifyRequest;
import com.mirae.commerce.member.dto.RegisterRequest;
import com.mirae.commerce.member.dto.WithdrawRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/find-all")
    public ResponseEntity<List<Member>> getMemberList() {
        return new ResponseEntity<>(
                memberService.getMemberList(),
                HttpStatus.OK
        );
    }

    @PostMapping("/registration")
    public ResponseEntity<Boolean> register(RegisterRequest registerRequest) {
        return new ResponseEntity<>(
                memberService.register(registerRequest),
                HttpStatus.OK
        );
    }

    @PostMapping("/modification")
    public ResponseEntity<Boolean> modify(@RequestBody @JwtRequired ModifyRequest modifyRequest) {
        return new ResponseEntity<>(
                memberService.modify(modifyRequest),
                HttpStatus.OK
        );
    }

    @PostMapping("/withdrawal")
    public ResponseEntity<Boolean> withdraw(WithdrawRequest withdrawRequest) {
        String username = (String) RequestContextHolder.getRequestAttributes().getAttribute("username", RequestAttributes.SCOPE_REQUEST);
        withdrawRequest.setUsername(username);
        if (username == null) {
            throw new RuntimeException();
        }
        return new ResponseEntity<>(
                memberService.withdraw(withdrawRequest),
                HttpStatus.OK
        );
    }

    @GetMapping("/email-confirm")
    public ResponseEntity<Boolean> confirmEmail(ConfirmEmailRequest confirmEmailRequest) {
        return new ResponseEntity<>(
                memberService.confirmEmail(confirmEmailRequest),
                HttpStatus.OK
        );
    }
}