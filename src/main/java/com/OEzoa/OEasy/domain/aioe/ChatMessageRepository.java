package com.OEzoa.OEasy.domain.aioe;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ChatMessageRepository extends JpaRepository<AiOeChatMessage, Long> {
    List<AiOeChatMessage> findByAiOeOrderByDateTimeAsc(AiOe aiOe); // 특정 챗봇에 연결된 메시지 조회
    void deleteByAiOe(AiOe aiOe);
    // 기존에 연결된 챗봇이 있나 탐색
    AiOeChatMessage findFirstByAiOeAndTypeOrderByDateTimeAsc(AiOe aiOe, String aioe);

    // Fetch Join 추가
    @Query("SELECT c FROM AiOeChatMessage c LEFT JOIN FETCH c.aiOe WHERE c.aiOe = :aiOe ORDER BY c.dateTime ASC")
    List<AiOeChatMessage> findByAiOeWithDetailsOrderByDateTimeAsc(@Param("aiOe") AiOe aiOe);
}
