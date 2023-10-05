package com.example.fureverhomes_project.controller;

import com.example.fureverhomes_project.dto.BoardPageDTO;
import com.example.fureverhomes_project.dto.BoardReqDTO;
import com.example.fureverhomes_project.dto.BoardResDTO;
import com.example.fureverhomes_project.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/fureverhomes")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board")
    public String viewBoardSearchPage() {
        return "html/furever_board_list";
    }

    @GetMapping("/board/{board_id}")
    public String viewBoardPage() {
        return "html/furever_board";
    }

    @GetMapping("/board/create")
    public String createBoardPage() {
        return "html/furever_board_create";
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

        //한개 게시글 보기
        @GetMapping("/board/{board_id}/view")
        public ResponseEntity<BoardResDTO> selectBoard(@PathVariable("board_id") Long boardId) {
            BoardResDTO boardResDTO = boardService.selectBoard(boardId);
            if(boardResDTO != null) return ResponseEntity.ok(boardResDTO);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        //전체 게시글 보기
        @GetMapping("/board.list")
        public ResponseEntity<BoardPageDTO> selectAllBoard(@RequestParam Map<String, String> params, Pageable pageRequest) {
            Sort sort = Sort.by(Sort.Order.desc("createdTime"));
            Pageable pageable = PageRequest.of(pageRequest.getPageNumber() - 1, pageRequest.getPageSize(), sort);
            BoardPageDTO boardResDTOS = boardService.selectAllBoard(params, pageable);
            return ResponseEntity.ok(boardResDTOS);
        }
    }
}
