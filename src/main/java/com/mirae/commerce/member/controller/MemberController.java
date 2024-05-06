package com.mirae.commerce.member.controller;
import com.mirae.commerce.auth.Role;
import com.mirae.commerce.auth.jwt.JwtRequired;
import com.mirae.commerce.auth.jwt.JwtUsernameInject;
import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.service.MemberService;
import com.mirae.commerce.member.dto.ConfirmEmailRequest;
import com.mirae.commerce.member.dto.UpdateRequest;
import com.mirae.commerce.member.dto.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<Boolean> createMember(@RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok()
                .body(memberService.register(registerRequest));
    }

    @JwtRequired(role = Role.USER)
    @PatchMapping
    public ResponseEntity<Boolean> updateMember(@JwtUsernameInject @RequestBody UpdateRequest updateRequest) {
        return ResponseEntity.ok()
                .body(memberService.update(updateRequest));
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Boolean> deleteMember(@PathVariable("username") String username) {
        return ResponseEntity.ok()
                .body(memberService.withdraw(username));
    }

    @GetMapping("/{username}")
    public ResponseEntity<Member> getMember(@PathVariable("username") String username) {
        return ResponseEntity.ok()
                .body(memberService.findMemberByUsername(username));
    }

    @JwtRequired(role = Role.ADMIN)
    @GetMapping
    public ResponseEntity<List<Member>> getMemberList() {
        return ResponseEntity.ok()
                .body(memberService.getMemberList());
    }

    @GetMapping("/get-authenticated")
    public ResponseEntity<Member> getAuthenticatedMember() {
        return ResponseEntity.ok()
                .body(memberService.getAuthenticatedMember());
    }

    @PatchMapping("/confirm-email")
    public ResponseEntity<Boolean> confirmEmail(ConfirmEmailRequest confirmEmailRequest) {
        return ResponseEntity.ok()
                .body(memberService.confirmEmail(confirmEmailRequest));
    }
}