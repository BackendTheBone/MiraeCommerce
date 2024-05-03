package com.mirae.commerce.member.entity;


import com.mirae.commerce.auth.Role;
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
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String realname;

    @Column
    private String email;

    @Column
    private String phone;

    @Column
    private String address;

    @Column
    private String status;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Role role;

    @Builder
    public Member(Long id, String username, String password, String realname, String email, String phone, String address, String status, Role role) {
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
    public void updateRole(Role role) {
        if (role != null) {
            this.role = role;
        }
    }
}
