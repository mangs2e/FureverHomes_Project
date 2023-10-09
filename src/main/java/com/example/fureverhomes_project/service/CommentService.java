package com.example.fureverhomes_project.service;

import com.example.fureverhomes_project.dto.CommentReqDTO;
import com.example.fureverhomes_project.entity.Board;
import com.example.fureverhomes_project.entity.Comment;
import com.example.fureverhomes_project.entity.Member;
import com.example.fureverhomes_project.repository.BoardRepository;
import com.example.fureverhomes_project.repository.CommentRepository;
import com.example.fureverhomes_project.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    //댓글 작성
    @Transactional
    public Long insertComment(final CommentReqDTO commentResDTO) {
        Member member = memberRepository.findById(commentResDTO.getMember_id()).orElseThrow(() -> new IllegalArgumentException("Member 객체가 없음"));
        Board board = boardRepository.findById(commentResDTO.getBoard_id()).orElseThrow(() -> new IllegalArgumentException("댓글 쓰기 실패 : Board 객체가 없음"));
        return commentRepository.save(commentResDTO.toEntity(member, board)).getId();
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(final Map<String, String> param) {
        Long commentId = Long.valueOf(param.get("comment_id"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("Comment 객체가 없음"));
        commentRepository.delete(comment);
    }
}
