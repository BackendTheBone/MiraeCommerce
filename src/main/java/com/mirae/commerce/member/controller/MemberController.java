package com.mirae.commerce.member.controller;
import com.mirae.commerce.auth.jwt.JwtRequired;
import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.service.MemberService;
import com.mirae.commerce.member.dto.ConfirmEmailRequest;
import com.mirae.commerce.member.dto.UpdateRequest;
import com.mirae.commerce.member.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/members")
    public ResponseEntity<Member> getAuthenticatedMember() {
        return new ResponseEntity<>(
                memberService.getAuthenticatedMember(),
                HttpStatus.OK
        );
    }

    @GetMapping("/members/{username}")
    public ResponseEntity<Member> getMember(@PathVariable("username") String username) {
        return new ResponseEntity<>(
                memberService.findMemberByUsername(username),
                HttpStatus.OK
        );
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMemberList() {
        return new ResponseEntity<>(
                memberService.getMemberList(),
                HttpStatus.OK
        );
    }

    @PostMapping("/members")
    public ResponseEntity<Boolean> signupMember(RegisterRequest registerRequest) {
        return new ResponseEntity<>(
                memberService.register(registerRequest),
                HttpStatus.OK
        );
    }

    @PutMapping("/members")
    public ResponseEntity<Boolean> updateMember(@RequestBody @JwtRequired UpdateRequest updateRequest) {
        return new ResponseEntity<>(
                memberService.update(updateRequest),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/members/{username}")
    public ResponseEntity<Boolean> deleteMember(@PathVariable("username") String username) {
        return new ResponseEntity<>(
                memberService.withdraw(username),
                HttpStatus.OK
        );
    }

    @PatchMapping("/email-confirm")
    public ResponseEntity<Boolean> confirmEmail(ConfirmEmailRequest confirmEmailRequest) {
        return new ResponseEntity<>(
                memberService.confirmEmail(confirmEmailRequest),
                HttpStatus.OK
        );
    }
}