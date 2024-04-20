package com.mirae.commerce.member.service;

import com.mirae.commerce.common.dto.ErrorCode;
import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.repository.MemberRepository;
import com.mirae.commerce.member.exception.MemberExceptionHandler;
import com.mirae.commerce.mail.service.MailService;
import com.mirae.commerce.common.config.WebConfig;
import com.mirae.commerce.member.dto.ConfirmEmailDto;
import com.mirae.commerce.member.dto.ModifyRequestDto;
import com.mirae.commerce.member.dto.RegisterRequestDto;
import com.mirae.commerce.member.dto.WithdrawRequestDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MailService mailService;
    private final EmailConfirmationManager emailConfirmationManager;

    @Override
    public List<Member> getMemberList() {
        return memberRepository.findAll();
    }

    @Override
    public Member findMember(String username) {
        return memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));
    }

    @Override
    public boolean register(RegisterRequestDto registerRequestDto) {
        if (memberRepository.findMemberByUsername(registerRequestDto.getUsername()).isPresent()) {
            return false;
        }

        try {
            UUID uuid = UUID.randomUUID();
            emailConfirmationManager.putConfirmation(uuid, registerRequestDto.getUsername());
            StringBuilder mailContent = new StringBuilder()
                    .append("<a href=\"")
                    .append(WebConfig.BASE_URL)
                    .append("/member/email-confirm?uuid=")
                    .append(uuid.toString())
                    .append("\">이메일 인증</a>");
            MimeMessage message = mailService.createMessage(registerRequestDto.getEmail(), "회원가입 인증", mailContent.toString());
            mailService.sendMail(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        registerRequestDto.setStatus("Unauthorized");
        memberRepository.save(registerRequestDto.toEntity());
        return true;
    }

    @Override
    public boolean modify(ModifyRequestDto modifyRequestDto) {
        Member member = memberRepository.findMemberByUsername(modifyRequestDto.getUsername())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        modifyRequestDto.setMemberId(member.getMemberId());
        memberRepository.save(modifyRequestDto.toEntity());
        return true;
    }

    @Override
    public boolean withdraw(WithdrawRequestDto withdrawRequestDto) {
        Member member = memberRepository.findMemberByUsername(withdrawRequestDto.getUsername())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        memberRepository.delete(member);
        return true;
    }

    @Override
    public boolean confirmEmail(ConfirmEmailDto confirmEmailDto) {
        UUID uuid = UUID.fromString(confirmEmailDto.getUuid());
        String username = emailConfirmationManager.getConfirmation(uuid);

        if (username == null) {
            return false;
        }
        emailConfirmationManager.removeConfirmation(uuid);

        Member member = memberRepository.findMemberByUsername(username)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        member.updateStatus("Authorized");
        memberRepository.save(member);

        return true;
    }
}
