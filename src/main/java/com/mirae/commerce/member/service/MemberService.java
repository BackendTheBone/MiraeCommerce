package com.mirae.commerce.member.service;

import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.dto.ConfirmEmailDto;
import com.mirae.commerce.member.dto.ModifyRequestDto;
import com.mirae.commerce.member.dto.RegisterRequestDto;
import com.mirae.commerce.member.dto.WithdrawRequestDto;

import java.util.List;

public interface MemberService {
    List<Member> getMemberList();
    Member findMember(String username);

    boolean register(RegisterRequestDto dto);
    boolean modify(ModifyRequestDto dto);
    boolean withdraw(WithdrawRequestDto dto);

    boolean confirmEmail(ConfirmEmailDto confirmEmailDto);
}
