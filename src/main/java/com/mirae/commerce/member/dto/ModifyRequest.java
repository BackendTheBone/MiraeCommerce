package com.mirae.commerce.member.dto;

import com.mirae.commerce.member.entity.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ModifyRequest {
    private Long id;
    private String username;
    private String password;
    private String realname;
    private String email;
    private String phone;
    private String address;
    private String status;
    private String role;

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

    public Member toEntity(Member member) {
        return Member.builder()
                .id(getDtoFieldIfNotNull(this.id, member.getId()))
                .username(getDtoFieldIfNotNull(this.username, member.getUsername()))
                .password(getDtoFieldIfNotNull(this.password, member.getPassword()))
                .realname(getDtoFieldIfNotNull(this.realname, member.getRealname()))
                .email(getDtoFieldIfNotNull(this.email, member.getEmail()))
                .phone(getDtoFieldIfNotNull(this.phone, member.getPhone()))
                .address(getDtoFieldIfNotNull(this.address, member.getAddress()))
                .status(getDtoFieldIfNotNull(this.status, member.getStatus()))
                .role(getDtoFieldIfNotNull(this.role, member.getRole()))
                .build();
    }

    private static <T> T getDtoFieldIfNotNull(final T dtoField, final T entityField) {
        return dtoField == null ? entityField : dtoField;
    }
}
