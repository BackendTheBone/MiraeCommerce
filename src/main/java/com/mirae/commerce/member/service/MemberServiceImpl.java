package com.mirae.commerce.member.service;

import com.mirae.commerce.common.dto.ErrorCode;
import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.repository.MemberRepository;
import com.mirae.commerce.member.exception.MemberExceptionHandler;
import com.mirae.commerce.mail.service.MailService;
import com.mirae.commerce.common.config.WebConfig;
import com.mirae.commerce.member.dto.ConfirmEmailRequest;
import com.mirae.commerce.member.dto.ModifyRequest;
import com.mirae.commerce.member.dto.RegisterRequest;
import com.mirae.commerce.member.dto.WithdrawRequest;
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
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));
    }

    @Override
    public boolean register(RegisterRequest registerRequest) {
        if (memberRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return false;
        }

        try {
            UUID uuid = UUID.randomUUID();
            emailConfirmationManager.putConfirmation(uuid, registerRequest.getUsername());
            StringBuilder mailContent = new StringBuilder()
                    .append("<a href=\"")
                    .append(WebConfig.BASE_URL)
                    .append("/member/email-confirm?uuid=")
                    .append(uuid.toString())
                    .append("\">이메일 인증</a>");
            MimeMessage message = mailService.createMessage(registerRequest.getEmail(), "회원가입 인증", mailContent.toString());
            mailService.sendMail(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        registerRequest.setStatus("Unauthorized");
        memberRepository.save(registerRequest.toEntity());
        return true;
    }

    @Override
    public boolean modify(ModifyRequest modifyRequest) {
        Member member = memberRepository.findByUsername(modifyRequest.getUsername())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        member = modifyRequest.toEntity(member);
        memberRepository.save(member);
        return true;
    }

    @Override
    public boolean withdraw(WithdrawRequest withdrawRequest) {
        Member member = memberRepository.findByUsername(withdrawRequest.getUsername())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        memberRepository.delete(member);
        return true;
    }

    @Override
    public boolean confirmEmail(ConfirmEmailRequest confirmEmailRequest) {
        UUID uuid = UUID.fromString(confirmEmailRequest.getUuid());
        String username = emailConfirmationManager.getConfirmation(uuid);

        if (username == null) {
            return false;
        }
        emailConfirmationManager.removeConfirmation(uuid);

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        member.updateStatus("Authorized");
        memberRepository.save(member);

        return true;
    }
}
