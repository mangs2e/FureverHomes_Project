package com.example.fureverhomes_project.entity;

import com.example.fureverhomes_project.entity.superClass.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
@ToString
@Setter
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

    @Column(nullable = false, columnDefinition = "int default 0")
    private int views; //조회수

}
