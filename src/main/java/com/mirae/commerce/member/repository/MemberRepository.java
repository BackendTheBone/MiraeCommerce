package com.mirae.commerce.member.repository;

import com.mirae.commerce.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>{
    Member findByUsername(String username);
}
