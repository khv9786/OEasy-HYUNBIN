package com.OEzoa.OEasy.domain.member;

import com.OEzoa.OEasy.domain.aioe.AiOe;
import com.OEzoa.OEasy.domain.aioe.AioeUsage;
import com.OEzoa.OEasy.domain.community.OeBoard;
import com.OEzoa.OEasy.domain.community.OeBoardComment;
import com.OEzoa.OEasy.domain.community.OeBoardLike;
import com.OEzoa.OEasy.domain.vote.OeChatting;
import com.OEzoa.OEasy.domain.vote.OeVote;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.persistence.FetchType;
import org.hibernate.annotations.BatchSize;

@Getter
@Entity
@Builder(toBuilder = true)
@Table(name = "member", schema = "oeasy")
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_pk", nullable = false)
    private Long memberPk;

    @Column(name = "kakao_id", unique = true)
    private Long kakaoId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "pw")
    private String pw;

    @Column(name = "salt")
    private String salt;

    @Column(name = "nick_name", length = 8)
    private String nickname;

    @Column(name = "member_image")
    private String memberImage;

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY) // 회원 삭제 시 MemberToken 삭제
    private MemberToken memberToken;

    @OneToOne(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private AiOe aiOe;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OeBoard> oeBoard;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OeBoardComment> oeBoardComments;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OeBoardLike> oeBoardLikes;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OeVote> oeVoting;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OeChatting> oeChatting;

    @BatchSize(size = 100)
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AioeUsage> usages = new ArrayList<>();
}