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
@RequestMapping("api")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseEntity<Boolean> createMember(@RequestBody RegisterRequest registerRequest) {
        return new ResponseEntity<>(
                memberService.register(registerRequest),
                HttpStatus.OK
        );
    }

    @PutMapping("/member")
    public ResponseEntity<Boolean> updateMember(@RequestBody UpdateRequest updateRequest) {
        return new ResponseEntity<>(
                memberService.update(updateRequest),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/member/{username}")
    public ResponseEntity<Boolean> deleteMember(@PathVariable("username") String username) {
        return new ResponseEntity<>(
                memberService.withdraw(username),
                HttpStatus.OK
        );
    }

    @GetMapping("/member/{username}")
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

    @GetMapping("/member")
    public ResponseEntity<Member> getAuthenticatedMember() {
        return new ResponseEntity<>(
                memberService.getAuthenticatedMember(),
                HttpStatus.OK
        );
    }

    @PatchMapping("/member/email-confirmation")
    public ResponseEntity<Boolean> confirmEmail(ConfirmEmailRequest confirmEmailRequest) {
        return new ResponseEntity<>(
                memberService.confirmEmail(confirmEmailRequest),
                HttpStatus.OK
        );
    }
}