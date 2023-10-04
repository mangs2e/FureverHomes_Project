package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.superClass.BaseEntity;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id; //게시판 아이디

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member; //연관관계 매핑 - 멤버 (작성자 필요)

    @Lob
    @Column(nullable = false)
    private String title; //게시판 제목

    @Lob
    @Column(nullable = false)
    private String content; //게시판 글

    @Column(nullable = false)
    private int views; //조회수

    @Builder
    public Board(Member member, String title, String content, int views) {
        this.member = member;
        this.title = title;
        this.content = content;
        this.views = views;
    }

}
