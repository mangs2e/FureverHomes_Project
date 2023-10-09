package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.CommentReqDTO;
import com.example.fureverhomes_project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/fureverhomes")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/board/{board_id}/comment")
    public ResponseEntity<Object> insertComment(@PathVariable("board_id") Long board_id, @RequestBody CommentReqDTO commentReqDTO, HttpServletRequest request) {
        Long member_id = (Long) request.getSession().getAttribute("loginMember");
        CommentReqDTO dto = CommentReqDTO.builder().board_id(board_id).member_id(member_id).comment(commentReqDTO.getComment()).build();
        System.out.println(commentReqDTO.getBoard_id());
        Long commentId = commentService.insertComment(dto);
        if (commentId != null) {
            return ResponseEntity.ok(commentId);
        }
        return ResponseEntity.internalServerError().build();
    }

    //댓글 삭제
    @DeleteMapping("/board/comment.delete")
    public ResponseEntity<Object> deleteComment(@RequestBody Map<String, String> param) {
        commentService.deleteComment(param);
        return ResponseEntity.ok().build();
    }
}
