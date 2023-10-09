package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.superClass.BaseEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //댓글 아이디

    @Lob
    @Column(nullable = false)
    private String comment; //댓글 내용

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board; //연관관계 매핑 - 게시판

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //연관관계 매핑 - 멤버 (댓글 작성자 필요)

    @Builder
    public Comment(Long id, String comment, Board board, Member member) {
        this.id = id;
        this.comment = comment;
        this.board = board;
        this.member = member;
    }

}
