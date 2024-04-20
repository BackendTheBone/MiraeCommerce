package com.mirae.commerce.common.exception.member;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "Member")
public class Member {
    @Id
    private long memberId;
    private String username;
    private String password;

}