package com.mirae.commerce.auth.service;

import com.mirae.commerce.auth.dto.RefreshTokenRequest;
import com.mirae.commerce.auth.dto.LoginRequest;
import com.mirae.commerce.auth.dto.LogoutRequest;
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
    public Jwt login(LoginRequest loginRequest) {
        Member member = memberRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new MemberExceptionHandler(ErrorCode.USERNAME_NOT_FOUND_ERROR));

        if (!member.checkUsernamePassword(loginRequest.getUsername(), loginRequest.getPassword())) {
            throw new MemberExceptionHandler(ErrorCode.PASSWORD_NOT_MATCH_ERROR);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getUsername());
        Jwt jwt = jwtProvider.createJwt(claims);

        refreshTokenManager.putRefreshToken(member.getUsername(), jwt.getRefreshToken());

        return jwt;
    }

    @Override
    public boolean logout(LogoutRequest logoutRequest) {
        refreshTokenManager.removeRefreshToken(logoutRequest.getUsername());
        return true;
    }

    @Override
    public Jwt refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String storedRefreshToken = refreshTokenManager.getRefreshToken(refreshTokenRequest.getUsername());

        if (storedRefreshToken == null) {
            throw new JwtExceptionHandler(ErrorCode.EXPIRED_REFRESH_TOKEN_EXCEPTION);
        }

        try {
            jwtProvider.getClaims(refreshTokenRequest.getRefreshToken());
        } catch (ExpiredJwtException e) {
            throw new JwtExceptionHandler("Refresh token does not match", ErrorCode.JWT_ERROR);
        }

        if (!storedRefreshToken.equals(refreshTokenRequest.getRefreshToken())) {
            throw new JwtExceptionHandler("No matching refresh token found", ErrorCode.JWT_ERROR);
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("username", refreshTokenRequest.getUsername());
        Jwt jwt = jwtProvider.createJwt(claims);

        refreshTokenManager.putRefreshToken(refreshTokenRequest.getUsername(), jwt.getRefreshToken());

        return jwt;
    }
}
