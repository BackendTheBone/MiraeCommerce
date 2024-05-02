package com.mirae.commerce.member.controller;
import com.mirae.commerce.auth.dto.LogoutRequest;
import com.mirae.commerce.auth.jwt.JwtRequired;
import com.mirae.commerce.auth.jwt.JwtUsernameInject;
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

    @PostMapping("/members")
    public ResponseEntity<Boolean> createMember(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok()
                .body(memberService.register(registerRequest));
    }

    @JwtRequired
    @PatchMapping("/members")
    public ResponseEntity<Boolean> updateMember(@JwtUsernameInject @RequestBody UpdateRequest updateRequest) {
        return ResponseEntity.ok()
                .body(memberService.update(updateRequest));
    }

    @DeleteMapping("/members/{username}")
    public ResponseEntity<Boolean> deleteMember(@PathVariable("username") String username) {
        return ResponseEntity.ok()
                .body(memberService.withdraw(username));
    }

    @GetMapping("/members/{username}")
    public ResponseEntity<Member> getMember(@PathVariable("username") String username) {
        return ResponseEntity.ok()
                .body(memberService.findMemberByUsername(username));
    }

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getMemberList() {
        return ResponseEntity.ok()
                .body(memberService.getMemberList());
    }

    @GetMapping("/members/get-authenticated")
    public ResponseEntity<Member> getAuthenticatedMember() {
        return ResponseEntity.ok()
                .body(memberService.getAuthenticatedMember());
    }

    @PatchMapping("/members/confirm-email")
    public ResponseEntity<Boolean> confirmEmail(ConfirmEmailRequest confirmEmailRequest) {
        return ResponseEntity.ok()
                .body(memberService.confirmEmail(confirmEmailRequest));
    }
}