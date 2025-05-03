package com.OEzoa.OEasy.domain.aioe;

import com.OEzoa.OEasy.domain.member.Member;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AioeRepository extends JpaRepository<AiOe, Long> {
    // 사용자 챗봇 가져오는 메서드
    Optional<AiOe> findByMember(Member member);

    // Fetch Join 추가
    @Query("SELECT a FROM AiOe a LEFT JOIN FETCH a.chatMessages WHERE a.member = :member")
    Optional<AiOe> findByMemberWithMessages(@Param("member") Member member);
}
