package com.example.fureverhomes_project.repository;

import com.example.fureverhomes_project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
