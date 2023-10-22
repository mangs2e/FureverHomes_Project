package com.example.fureverhomes_project.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class File {

    @Id @GeneratedValue
    @Column(name = "FILE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    private Board board;

    private String original_name; //원본 이름

    private String save_name; //저장 이름

    private Long size; //파일 사이즈

    private String file_path; //파일 경로

    private LocalDate createDate = LocalDate.now(); //생성 날짜

    private Boolean isDelete = false; //삭제 여부

    @Builder
    public File(String original_name, String save_name, Long size, String file_path) {
        this.original_name = original_name;
        this.save_name = save_name;
        this.size = size;
        this.file_path = file_path;
    }

    public void setBoard(Board board) {
        this.board = board;

        if(!board.getFiles().contains(this))
            board.getFiles().add(this);
    }

    public void updateDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}
