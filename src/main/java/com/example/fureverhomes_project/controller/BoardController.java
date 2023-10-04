package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.BoardReqDTO;
import com.example.fureverhomes_project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/fureverhomes")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/create")
    public String createBoardPage() {
        return "html/furever_board_create";
    }

    @GetMapping("/board/view")
    public String viewBoardPage() {
        return "html/furever_board";
    }



    @RestController
    @RequestMapping("/fureverhomes")
    public class BoardRestController {

        //게시판 등록
        @PostMapping("/board.create")
        public ResponseEntity<Long> insertBoard(@RequestBody BoardReqDTO boardReqDTO, HttpServletRequest request) {
            Long boardId = boardService.insertBoard((Long) request.getSession().getAttribute("loginMember"), boardReqDTO);
            if (boardId == null) {
                return ResponseEntity.internalServerError().build();
            }
            return ResponseEntity.ok(boardId);
        }
    }
}
