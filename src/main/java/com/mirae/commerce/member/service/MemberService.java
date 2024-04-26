package com.mirae.commerce.member.service;

import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.dto.ConfirmEmailRequest;
import com.mirae.commerce.member.dto.UpdateRequest;
import com.mirae.commerce.member.dto.RegisterRequest;

import java.util.List;

public interface MemberService {
    Member findMemberByUsername(String username);
    List<Member> getMemberList();
    Member findMember(String username);

    boolean register(RegisterRequest dto);
    boolean update(UpdateRequest dto);
    boolean withdraw(String username);

    boolean confirmEmail(ConfirmEmailRequest confirmEmailRequest);

    Member getAuthenticatedMember();
}
