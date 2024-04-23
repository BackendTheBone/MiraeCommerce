package com.mirae.commerce.member.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate // save 동작 시, null 필드는 반영하지 않고 update
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    // TODO. 어노테이션 name 지우기 <- 무슨 뜻?
    // TODO. 이름수정 <- 무슨 이름?

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "realname")
    private String realname;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

    @Builder
    public Member(Long id, String username, String password, String realname, String email, String phone, String address, String status, String role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.status = status;
        this.role = role;
    }

    public boolean checkUsernamePassword(String username, String password) {
        if (!username.equals(this.username)) {
            return false;
        }
        if (!password.equals(this.password)) {
            return false;
        }
        return true;
    }

    public void updateStatus(String status) {
        this.status = status;
    }
}
