package com.OEzoa.OEasy.domain.member;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
    Optional<Member> findByKakaoId(Long kakaoId);

    boolean existsByNickname(String newNickname);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.aiOe LEFT JOIN FETCH m.usages WHERE m.email = :email")
    Optional<Member> findByEmailWithAiOeAndUsages(@Param("email") String email);

    @Query("SELECT m FROM Member m LEFT JOIN FETCH m.aiOe LEFT JOIN FETCH m.usages WHERE m.kakaoId = :kakaoId")
    Optional<Member> findByKakaoIdWithAiOeAndUsages(@Param("kakaoId") Long kakaoId);
}
