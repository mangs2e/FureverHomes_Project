package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findByTitle(String title);

    @Query("SELECT b FROM Board b LEFT JOIN FETCH b.files f WHERE b.id = :boardId AND f.isDelete = false")
    Board findBoardWithNonDeleteFiles(@Param("boardId") Long boardId);
}
