package com.mirae.commerce.member.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@DynamicUpdate
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

    public void update(Member m) {
        updatePassword(m.password);
        updateRealname(m.realname);
        updateEmail(m.email);
        updatePhone(m.phone);
        updateAddress(m.address);
        updateStatus(m.status);
        updateRole(m.role);
    }

    public void updatePassword(String password) {
        if (password != null) {
            this.password = password;
        }
    }
    public void updateRealname(String realname) {
        if (realname != null) {
            this.realname = realname;
        }
    }
    public void updateEmail(String email) {
        if (email != null) {
            this.email = email;
        }
    }
    public void updatePhone(String phone) {
        if (phone != null) {
            this.phone = phone;
        }
    }
    public void updateAddress(String address) {
        if (address != null) {
            this.address = address;
        }
    }
    public void updateStatus(String status) {
        if (status != null) {
            this.status = status;
        }
    }
    public void updateRole(String role) {
        if (role != null) {
            this.role = role;
        }
    }
}
