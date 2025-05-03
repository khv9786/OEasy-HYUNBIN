package com.OEzoa.OEasy.domain.aioe;

import com.OEzoa.OEasy.domain.member.Member;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AioeUsageRepository extends JpaRepository<AioeUsage, AioeUsageId> {
    Optional<AioeUsage> findByMemberAndUsageDate(Member member, LocalDate usageDate);

    @Query("SELECT u FROM AioeUsage u LEFT JOIN FETCH u.member WHERE u.member = :member AND u.usageDate = :usageDate")
    Optional<AioeUsage> findByMemberAndUsageDateWithDetails(@Param("member") Member member, @Param("usageDate") LocalDate usageDate);
}

