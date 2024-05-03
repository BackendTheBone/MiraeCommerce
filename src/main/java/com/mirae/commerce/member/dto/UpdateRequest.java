package com.mirae.commerce.member.dto;

import com.mirae.commerce.auth.Role;
import com.mirae.commerce.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UpdateRequest {
    private Long id;
    private String username;
    private String password;
    private String realname;
    private String email;
    private String phone;
    private String address;
    private String status;
    private Role role;

    public Member toEntity() {
        return Member.builder()
                .id(id)
                .username(username)
                .password(password)
                .realname(realname)
                .email(email)
                .phone(phone)
                .address(address)
                .status(status)
                .role(role)
                .build();
    }
}
