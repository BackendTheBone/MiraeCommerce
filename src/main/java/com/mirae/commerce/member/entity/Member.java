package com.mirae.commerce.member.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

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

	public boolean checkUsernamePassword(String username, String password) {
		if(!username.equals(this.username)) {
			return false;
		}
		if(!password.equals(this.password)) {
			return false;
		}
		return true;
	}
}