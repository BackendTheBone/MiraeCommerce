package com.mirae.commerce.auth.service;

import com.mirae.commerce.auth.dto.RefreshTokenDto;
import com.mirae.commerce.auth.dto.LoginRequestDto;
import com.mirae.commerce.auth.dto.LogoutRequestDto;
import com.mirae.commerce.auth.jwt.Jwt;
import com.mirae.commerce.auth.jwt.JwtProvider;
import com.mirae.commerce.auth.jwt.RefreshTokenManager;
import com.mirae.commerce.common.dto.ErrorCode;
import com.mirae.commerce.auth.exception.JwtExceptionHandler;
import com.mirae.commerce.member.exception.MemberExceptionHandler;
import com.mirae.commerce.member.entity.Member;
import com.mirae.commerce.member.repository.MemberRepository;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;
    private final RefreshTokenManager refreshTokenManager;

    @Override
    public Jwt login(LoginRequestDto loginRequestDto) {
        Member member = memberRepository.findMemberByUsername(loginRequestDto.getUsername())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        if (!member.checkUsernamePassword(loginRequestDto.getUsername(), loginRequestDto.getPassword())) {
            throw new MemberExceptionHandler(ErrorCode.PASSWORD_NOT_MATCH_ERROR);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getUsername());
        Jwt jwt = jwtProvider.createJwt(claims);

        refreshTokenManager.putRefreshToken(member.getUsername(), jwt.getRefreshToken());

        return jwt;
    }

    @Override
    public boolean logout(LogoutRequestDto logoutRequestDto) {
        refreshTokenManager.removeRefreshToken(logoutRequestDto.getUsername());
        return true;
    }

    @Override
    public Jwt refreshToken(RefreshTokenDto refreshTokenDto) {
        String storedRefreshToken = refreshTokenManager.getRefreshToken(refreshTokenDto.getUsername());

        // TODO : storedRefreshToken null 예외 추가 할 것
        if (storedRefreshToken == null) return null;

        try {
            jwtProvider.getClaims(refreshTokenDto.getRefreshToken());
        } catch (ExpiredJwtException e) {
            throw new JwtExceptionHandler(ErrorCode.EXPIRED_REFRESH_TOKEN_EXCEPTION);
        }

        if (!storedRefreshToken.equals(refreshTokenDto.getRefreshToken())) {
            // TODO : refresh token 불일치 예외 추가 할 것
            return null;
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", refreshTokenDto.getUsername());
        Jwt jwt = jwtProvider.createJwt(claims);

        refreshTokenManager.putRefreshToken(refreshTokenDto.getUsername(), jwt.getRefreshToken());

        return jwt;
    }
}
