package com.OEzoa.OEasy.domain.aioe;

import static org.assertj.core.api.Assertions.assertThat;

import com.OEzoa.OEasy.domain.member.Member;
import com.OEzoa.OEasy.domain.member.MemberRepository;
import jakarta.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
class AioeRepositoryTest {
    private final Random random = new Random();
    @Autowired
    private AioeRepository aioeRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("AiOe 조회시 N+1 문제 발생 테스트")
    void findByMemberTest() {
        // Given
        Member member = Member.builder()
                .email("test@test.com")
                .nickname("user1")
                .build();
        memberRepository.save(member);

        // AiOe와 채팅 메시지 생성
        AiOe aiOe = AiOe.builder()
                .member(member)
                .build();

        // 채팅 메시지 100개 생성
        List<AiOeChatMessage> messages = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            messages.add(AiOeChatMessage.builder()
                    .aiOe(aiOe)
                    .message("test message " + i)
                    .dateTime(LocalDateTime.now().minusMinutes(i))
                    .type(i % 2 == 0 ? "user" : "assistant")
                    .build());
        }

        entityManager.persist(aiOe);
        messages.forEach(entityManager::persist);

        entityManager.flush();
        entityManager.clear();

        // When & Then
        System.out.println("\n=== 일반 findByMember 조회 시작 ===");
        Optional<AiOe> aiOeWithoutFetch = aioeRepository.findByMember(member);
        aiOeWithoutFetch.ifPresent(a -> {
            System.out.println("AiOe PK: " + a.getAioePk());
            System.out.println("채팅 메시지 조회 시작 ---");
            List<AiOeChatMessage> chatMessages = a.getChatMessages();
            System.out.println("총 메시지 수: " + chatMessages.size());
            chatMessages.stream()
                    .sorted(Comparator.comparing(AiOeChatMessage::getDateTime).reversed())
                    .limit(5)
                    .forEach(msg ->
                            System.out.printf("시간: %s, 타입: %s, 메시지: %s%n",
                                    msg.getDateTime(), msg.getType(), msg.getMessage())
                    );
            System.out.println("... 외 " + (chatMessages.size() - 5) + "개의 메시지");
        });
        System.out.println("=== 일반 findByMember 조회 종료 ===\n");

        System.out.println("\n=== Fetch Join을 사용한 조회 시작 ===");
        Optional<AiOe> aiOeWithFetch = aioeRepository.findByMemberWithMessages(member);
        aiOeWithFetch.ifPresent(a -> {
            System.out.println("AiOe PK: " + a.getAioePk());
            System.out.println("채팅 메시지 조회 시작 ---");
            List<AiOeChatMessage> chatMessages = a.getChatMessages();
            System.out.println("총 메시지 수: " + chatMessages.size());
            chatMessages.stream()
                    .sorted(Comparator.comparing(AiOeChatMessage::getDateTime).reversed())
                    .limit(5)
                    .forEach(msg ->
                            System.out.printf("시간: %s, 타입: %s, 메시지: %s%n",
                                    msg.getDateTime(), msg.getType(), msg.getMessage())
                    );
            System.out.println("... 외 " + (chatMessages.size() - 5) + "개의 메시지");
        });
        System.out.println("=== Fetch Join을 사용한 조회 종료 ===\n");

        // 검증
        assertThat(aiOeWithFetch).isPresent();
        AiOe fetchedAiOe = aiOeWithFetch.get();
        assertThat(fetchedAiOe.getChatMessages()).hasSize(100);
    }
}