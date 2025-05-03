package com.OEzoa.OEasy.domain.member;

import static org.assertj.core.api.Assertions.assertThat;

import com.OEzoa.OEasy.domain.aioe.AiOe;
import com.OEzoa.OEasy.domain.aioe.AioeUsage;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("findByEmail 사용 시 N+1 문제 발생 테스트")
    void findByEmailTest() {
        // Given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("user1")
                .kakaoId(12345L)
                .build();

        AiOe aiOe = AiOe.builder()
                .member(member)
                .build();

        AioeUsage usage = AioeUsage.builder()
                .member(member)
                .usageDate(LocalDate.now())
                .usageCount(5)
                .build();

        memberRepository.save(member);
        entityManager.persist(aiOe);
        entityManager.persist(usage);

        entityManager.flush();
        entityManager.clear();

        // When & Then
        System.out.println("\n=== findByEmail 조회 시작 ===");
        Optional<Member> foundMember = memberRepository.findByEmail("test@test.com");
        foundMember.ifPresent(m -> {
            System.out.println("Member email: " + m.getEmail());
            System.out.println("AiOe 조회: " + (m.getAiOe() != null));
            System.out.println("Usages 조회: " + m.getUsages().size());
        });
        System.out.println("=== findByEmail 조회 종료 ===\n");
    }

    @Test
    @DisplayName("findByKakaoId 사용 시 N+1 문제 발생 테스트")
    void findByKakaoIdTest() {
        // Given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("user1")
                .kakaoId(12345L)
                .build();

        AiOe aiOe = AiOe.builder()
                .member(member)
                .build();

        AioeUsage usage = AioeUsage.builder()
                .member(member)
                .usageDate(LocalDate.now())
                .usageCount(5)
                .build();

        memberRepository.save(member);
        entityManager.persist(aiOe);
        entityManager.persist(usage);

        entityManager.flush();
        entityManager.clear();

        // When & Then
        System.out.println("\n=== findByKakaoId 조회 시작 ===");
        Optional<Member> foundMember = memberRepository.findByKakaoId(12345L);
        foundMember.ifPresent(m -> {
            System.out.println("Member kakaoId: " + m.getKakaoId());
            System.out.println("AiOe 조회: " + (m.getAiOe() != null));
            System.out.println("Usages 조회: " + m.getUsages().size());
        });
        System.out.println("=== findByKakaoId 조회 종료 ===\n");
    }
}