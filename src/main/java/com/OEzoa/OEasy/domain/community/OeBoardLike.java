package com.OEzoa.OEasy.domain.community;

import com.OEzoa.OEasy.domain.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "oe_board_like", schema = "oeasy")
public class OeBoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_like_pk", nullable = false)
    private Long boardLikePk;

    @ManyToOne
    @JoinColumn(name = "board_pk", nullable = false)
    private OeBoard board;

    @ManyToOne
    @JoinColumn(name = "user_pk", nullable = false)
    private User user;
}
