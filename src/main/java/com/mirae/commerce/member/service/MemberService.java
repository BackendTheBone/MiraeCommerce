package com.mirae.commerce.member.service;

import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.dto.ConfirmEmailRequest;
import com.mirae.commerce.member.dto.ModifyRequest;
import com.mirae.commerce.member.dto.RegisterRequest;
import com.mirae.commerce.member.dto.WithdrawRequest;

import java.util.List;

public interface MemberService {
    List<Member> getMemberList();
    Member findMember(String username);

    boolean register(RegisterRequest dto);
    boolean modify(ModifyRequest dto);
    boolean withdraw(WithdrawRequest dto);

    boolean confirmEmail(ConfirmEmailRequest confirmEmailRequest);
}
